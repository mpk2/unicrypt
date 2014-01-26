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
package ch.bfh.unicrypt.math.algebra.general.interfaces;

import ch.bfh.unicrypt.crypto.random.classes.ReferenceRandomByteSequence;
import ch.bfh.unicrypt.crypto.random.interfaces.RandomByteSequence;

/**
 * This interface represents the concept a cyclic atomic group. Every element of a cyclic group can be written as a
 * power of some particular element in multiplicative notation, or as a multiple of the element in additive notation.
 * Such an element is called generator of the group. For every positive integer there is exactly one cyclic group (up to
 * isomorphism) with that order, and there is exactly one infinite cyclic group. This interface extends extends
 * {@link Group} with additional methods for dealing with generators. Each implementing class must provide a default
 * generator.
 * <p>
 * @see "Handbook of Applied Cryptography, Definition 2.167"
 * <p>
 * @author R. Haenni
 * @author R. E. Koenig
 * @version 2.0
 */
public interface CyclicGroup
	   extends Group {

	/**
	 * Returns a default generator of this group.
	 * <p>
	 * @return The default generator
	 */
	public Element getDefaultGenerator();

	/**
	 * Returns a randomly selected generator of this group using the default random generator.
	 * <p>
	 * @return The randomly selected generator
	 */
	public Element getRandomGenerator();

	/**
	 * Returns a randomly selected generator of this group using a given random generator.
	 * <p>
	 * @param randomGenerator The given random generator
	 * @return The randomly selected generator
	 */
	public Element getRandomGenerator(RandomByteSequence randomByteSequence);

	public Element getIndependentGenerator(int index);

	public Element getIndependentGenerator(int index, ReferenceRandomByteSequence referenceRandomByteSequence);

	public Element[] getIndependentGenerators(int maxIndex);

	public Element[] getIndependentGenerators(int maxIndex, ReferenceRandomByteSequence referenceRandomByteSequence);

	public Element[] getIndependentGenerators(int minIndex, int maxIndex);

	public Element[] getIndependentGenerators(int minIndex, int maxIndex, ReferenceRandomByteSequence referenceRandomByteSequence);

	/**
	 * Checks if a given element is a generator of the group.
	 * <p>
	 * @param element The given element
	 * @return {@code true} if the element is a generator, {@code false} otherwise
	 * @throws IllegalArgumentException if {@code} is null
	 */
	public boolean isGenerator(Element element);

}
