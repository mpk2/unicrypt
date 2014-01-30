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
package ch.bfh.unicrypt.crypto.mixer;

import ch.bfh.unicrypt.crypto.mixer.classes.IdentityMixer;
import ch.bfh.unicrypt.math.algebra.general.classes.PermutationElement;
import ch.bfh.unicrypt.math.algebra.general.classes.ProductGroup;
import ch.bfh.unicrypt.math.algebra.general.classes.Tuple;
import ch.bfh.unicrypt.math.algebra.general.interfaces.CyclicGroup;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import ch.bfh.unicrypt.math.algebra.multiplicative.classes.GStarModSafePrime;

/**
 *
 * @author philipp
 */
public class IdentityMixerExample {

	public static void Example1() {

		// P R E P A R E
		//---------------
		// Create cyclic group (modulo 20 bits)
		CyclicGroup G_q = GStarModSafePrime.getRandomInstance(20);

		// Set size
		int size = 10;

		// Create identities
		Tuple identities = ProductGroup.getInstance(G_q, size).getRandomElement();

		// S H U F F L E
		//---------------
		// Create mixer and shuffle
		IdentityMixer mixer = IdentityMixer.getInstance(G_q, size);
		Tuple shuffledIdentities = mixer.shuffle(identities);

		System.out.println("Identities:          " + identities);
		System.out.println("Shuffled Identities: " + shuffledIdentities);
	}

	public static void Example2() {

		// P R E P A R E
		//---------------
		// Create cyclic group (modulo 20 bits)
		CyclicGroup G_q = GStarModSafePrime.getRandomInstance(20);

		// Set size
		int size = 10;

		// Create identities
		Tuple identities = ProductGroup.getInstance(G_q, size).getRandomElement();

		// S H U F F L E
		//---------------
		// Create mixer and shuffle
		IdentityMixer mixer = IdentityMixer.getInstance(G_q, size);

		// Create permutation
		PermutationElement permutation = mixer.getPermutationGroup().getRandomElement();

		// Create randomization
		Element randomization = mixer.generateRandomization();

		Tuple shuffledIdentities = mixer.shuffle(identities, permutation, randomization);

		System.out.println("Identities:          " + identities);
		System.out.println("Shuffled Identities: " + shuffledIdentities);
	}

	public static void main(String args[]) {

		System.out.println("\nIdentity Shuffle Example 1 (plain):");
		IdentityMixerExample.Example1();

		System.out.println("\nIdentity Shuffle Example 2 (generate permutation/randomization beforehand):");
		IdentityMixerExample.Example2();
	}

}