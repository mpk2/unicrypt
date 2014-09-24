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
package ch.bfh.unicrypt.crypto.proofsystem.challengegenerator.classes;

import ch.bfh.unicrypt.crypto.proofsystem.challengegenerator.abstracts.AbstractNonInteractiveChallengeGenerator;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Set;
import ch.bfh.unicrypt.random.classes.PseudoRandomOracle;
import ch.bfh.unicrypt.random.classes.ReferenceRandomByteSequence;
import ch.bfh.unicrypt.random.interfaces.RandomOracle;

public class FiatShamirChallengeGenerator
	   extends AbstractNonInteractiveChallengeGenerator {

	protected final RandomOracle randomOracle;

	protected FiatShamirChallengeGenerator(Set inputSpace, Set challengeSpace, Element proverId, final RandomOracle randomOracle) {
		super(inputSpace, challengeSpace, proverId);
		this.randomOracle = randomOracle;
	}

	public RandomOracle getRandomOracle() {
		return this.randomOracle;
	}

	@Override
	protected Element abstractAbstractGenerate(Element input) {
		// TODO: One should be able to specify the ConvertMethod
		ReferenceRandomByteSequence randomByteSequence = this.randomOracle.getReferenceRandomByteSequence(input.getByteArray());
		return this.getChallengeSpace().getRandomElement(randomByteSequence);
	}

	public static FiatShamirChallengeGenerator getInstance(final Set inputSpace, Set challengeSpace) {
		return FiatShamirChallengeGenerator.getInstance(inputSpace, challengeSpace, PseudoRandomOracle.getInstance());
	}

	public static FiatShamirChallengeGenerator getInstance(final Set inputSpace, Set challengeSpace, Element proverId) {
		return FiatShamirChallengeGenerator.getInstance(inputSpace, challengeSpace, proverId, PseudoRandomOracle.getInstance());
	}

	public static FiatShamirChallengeGenerator getInstance(final Set inputSpace, Set challengeSpace, RandomOracle randomOracle) {
		return FiatShamirChallengeGenerator.getInstance(inputSpace, challengeSpace, (Element) null, randomOracle);
	}

	public static FiatShamirChallengeGenerator getInstance(final Set inputSpace, Set challengeSpace, Element proverId, RandomOracle randomOracle) {
		if (inputSpace == null || challengeSpace == null || randomOracle == null) {
			throw new IllegalArgumentException();
		}
		return new FiatShamirChallengeGenerator(inputSpace, challengeSpace, proverId, randomOracle);
	}

}
