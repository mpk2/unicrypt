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
package ch.bfh.unicrypt.crypto.random;

import ch.bfh.unicrypt.crypto.random.classes.PseudoRandomOracle;
import ch.bfh.unicrypt.crypto.random.classes.ReferenceRandomByteSequence;
import ch.bfh.unicrypt.math.helper.ByteArray;
import java.math.BigInteger;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Rolf Haenni <rolf.haenni@bfh.ch>
 */
public class PseudoRandomReferenceStringTest {

	@Test
	public void generalTest() {
		ReferenceRandomByteSequence rrs = ReferenceRandomByteSequence.getInstance();
		BigInteger prime = rrs.getRandomNumberGenerator().nextPrime(10);
		rrs.getRandomNumberGenerator().nextBoolean();
		rrs.getRandomNumberGenerator().nextBytes(5);
		rrs.getRandomNumberGenerator().nextBytes(2048);
		rrs.reset();
		Assert.assertEquals(prime, rrs.getRandomNumberGenerator().nextPrime(10));
		rrs = PseudoRandomOracle.getInstance().getReferenceRandomByteSequence(ByteArray.getInstance(new byte[]{10, 5, 120}));
		prime = rrs.getRandomNumberGenerator().nextPrime(10);
		rrs.getRandomNumberGenerator().nextBoolean();
		rrs.getRandomNumberGenerator().nextBytes(5);
		rrs.reset();
		Assert.assertEquals(prime, rrs.getRandomNumberGenerator().nextPrime(10));
	}

}