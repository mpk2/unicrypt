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

import ch.bfh.unicrypt.crypto.random.classes.HybridRandomByteSequence;
import ch.bfh.unicrypt.crypto.random.interfaces.RandomByteSequence;
import ch.bfh.unicrypt.math.helper.HashMethod;
import java.util.Arrays;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TrueRandomNumberGeneratorExample {

	public static void main(String[] args) {

		//Get instance of a Default-TrueRandomGenerator with a forwardSecurity of 64bit and a security of 16bit. (A very bad one)
		RandomByteSequence randomGenerator = HybridRandomByteSequence.getInstance(HashMethod.DEFAULT, 8, 2);
		System.out.println("Seeded very fast!");
		//Get instance of a Default-TrueRandomGenerator (forwardSecurity: Half the strength of the HashMethod.DEFAULT and a security of HashMethod.DEFAULT)
		randomGenerator = HybridRandomByteSequence.getInstance();
		System.out.println("Seeded very slow");

		//The time to wait in order to get the ephemeral key is not bound to the requested amount of randomization
		//It is bound to the 'entropy-collection' of the DataCollector that happens inside every request of randomization.
		//This 'guarantees' Forward-Security.
		System.out.println(Arrays.toString(randomGenerator.getRandomNumberGenerator().nextBytes(256)));
		System.out.println(randomGenerator.getRandomNumberGenerator().nextBoolean());
		System.out.println(Arrays.toString(randomGenerator.getRandomNumberGenerator().nextBytes(256)));
		System.out.println(randomGenerator.getRandomNumberGenerator().nextBoolean());
		System.out.println(randomGenerator.getRandomNumberGenerator().nextBigInteger(2048));

		//Stopping the internal Thread which helps in acquiring fresh 'ephemeral' data
		System.exit(0);
	}

}