/*
 * UniCrypt
 *
 *  UniCrypt(tm) : Cryptographical framework allowing the implementation of cryptographic protocols e.g. e-voting
 *  Copyright (C) 2014 Bern University of Applied Sciences (BFH), Research Institute for
 *  Security in the Information Society (RISIS), E-Voting Group (EVG)
 *  Quellgasse 21, CH-2501 Biel, Switzerland
 *
 *  Licensed under Dual License consisting of:
 *  1. GNU Affero General Public License (AGPL) v3
 *  and
 *  2. Commercial license
 *
 *
 *  1. This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *  2. Licensees holding valid commercial licenses for UniCrypt may use this file in
 *   accordance with the commercial license agreement provided with the
 *   Software or, alternatively, in accordance with the terms contained in
 *   a written agreement between you and Bern University of Applied Sciences (BFH), Research Institute for
 *   Security in the Information Society (RISIS), E-Voting Group (EVG)
 *   Quellgasse 21, CH-2501 Biel, Switzerland.
 *
 *
 *   For further information contact <e-mail: unicrypt@bfh.ch>
 *
 *
 * Redistributions of files must retain the above copyright notice.
 */
package ch.bfh.unicrypt.crypto.schemes.padding.abstracts;

import ch.bfh.unicrypt.math.algebra.concatenative.classes.ByteArrayElement;
import ch.bfh.unicrypt.math.algebra.concatenative.classes.ByteArrayMonoid;
import ch.bfh.unicrypt.math.function.abstracts.AbstractFunction;
import ch.bfh.unicrypt.math.function.interfaces.Function;
import ch.bfh.unicrypt.random.interfaces.RandomByteSequence;

/**
 *
 * @author Rolf Haenni <rolf.haenni@bfh.ch>
 */
public abstract class AbstractByteArrayPaddingScheme
	   extends AbstractPaddingScheme<ByteArrayMonoid, ByteArrayElement> {

	protected final ByteArrayMonoid byteArrayMonoid;

	protected AbstractByteArrayPaddingScheme(ByteArrayMonoid byteArrayMonoid) {
		this.byteArrayMonoid = byteArrayMonoid;
	}

	@Override
	protected Function abstractGetPaddingFunction() {
		return new AbstractFunction<ByteArrayMonoid, ByteArrayElement, ByteArrayMonoid, ByteArrayElement>(ByteArrayMonoid.getInstance(), this.byteArrayMonoid) {
			@Override
			protected ByteArrayElement abstractApply(ByteArrayElement element, RandomByteSequence randomByteSequence) {
				int paddingLength = getPaddingLength(element);
				byte[] paddingBytes = new byte[paddingLength];
				int minIndex = 0;
				int maxIndex = paddingLength - 1;
				Byte separator = abstractGetSeparator();
				if (separator != null) {
					paddingBytes[minIndex] = separator;
					minIndex++;
				}
				if (abstractEndsWithLength()) {
					paddingBytes[maxIndex] = (byte) paddingLength;
					maxIndex--;
				}
				for (int i = minIndex; i <= maxIndex; i++) {
					paddingBytes[i] = abstractGetFiller(paddingLength, randomByteSequence);
				}
				return element.concatenate(ByteArrayMonoid.getInstance().getElement(paddingBytes));
			}
		};
	}

	protected int getPaddingLength(ByteArrayElement message) {
		Byte separator = this.abstractGetSeparator();
		int minPaddingLength = (separator == null ? 0 : 1) + (this.abstractEndsWithLength() ? 1 : 0);
		int paddingLength = (this.getBlockLength() - message.getLength() % this.getBlockLength()) % this.getBlockLength();
		if (paddingLength < minPaddingLength) {
			return this.getBlockLength() + paddingLength;
		}
		return paddingLength;
	}

	// returns null if no separator exists
	protected abstract Byte abstractGetSeparator();

	protected abstract byte abstractGetFiller(int paddingLength, RandomByteSequence randomByteSequence);

	// returns true if the last padding byte is the padding length
	protected abstract boolean abstractEndsWithLength();

}
