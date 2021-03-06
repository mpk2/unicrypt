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
package ch.bfh.unicrypt.math.algebra.dualistic;

import ch.bfh.unicrypt.helper.distribution.UniformDistribution;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.Z;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZElement;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZMod;
import java.math.BigInteger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author philipp
 */
public class ZTest {

	@Test
	public void testGetRandomElement() {
		Z z = Z.getInstance();
		try {
			z.getRandomElement();
			fail();
		} catch (UnsupportedOperationException e) {
		}

		z = Z.getInstance(UniformDistribution.getInstance(BigInteger.valueOf(10)));
		for (int i = 0; i < 100; i++) {
			ZElement e = z.getRandomElement();
			assertTrue(e.getValue().intValue() >= 0 && e.getValue().intValue() <= 10);
		}

		z = Z.getInstance(BigInteger.valueOf(11));
		for (int i = 0; i < 100; i++) {
			ZElement e = z.getRandomElement();
			assertTrue(e.getValue().intValue() >= 0 && e.getValue().intValue() <= 10);
		}

		z = Z.getInstance(UniformDistribution.getInstance(BigInteger.valueOf(-10), BigInteger.valueOf(10)));
		for (int i = 0; i < 100; i++) {
			ZElement e = z.getRandomElement();
			assertTrue(e.getValue().intValue() >= -10 && e.getValue().intValue() <= 10);
		}

		z = Z.getInstance(UniformDistribution.getInstance(4));
		for (int i = 0; i < 100; i++) {
			ZElement e = z.getRandomElement();
			assertTrue(e.getValue().intValue() >= 0 && e.getValue().intValue() <= 15);
		}

		z = Z.getInstance(UniformDistribution.getInstance(5, 5));
		for (int i = 0; i < 100; i++) {
			ZElement e = z.getRandomElement();
			assertTrue(e.getValue().intValue() >= 15 && e.getValue().intValue() <= 31);
		}
	}

	@Test
	public void testEquals() {
		Z z1 = Z.getInstance();
		Z z2 = Z.getInstance();
		assertEquals(z1, z2);

		z1 = Z.getInstance(UniformDistribution.getInstance(0, 5));
		z2 = Z.getInstance(UniformDistribution.getInstance(5));
		assertEquals(z1, z2);

		z1 = Z.getInstance(UniformDistribution.getInstance(0, 5));
		z2 = Z.getInstance(UniformDistribution.getInstance(3, 5));
		assertNotSame(z1, z2);
	}

	@Test
	public void testIsEquivalent() {
		Z z1 = Z.getInstance();
		Z z2 = Z.getInstance();
		assertTrue(z1.isEquivalent(z2));

		z2 = Z.getInstance(UniformDistribution.getInstance(5));
		assertTrue(z1.isEquivalent(z2));

		z1 = Z.getInstance(UniformDistribution.getInstance(0, 5));
		assertTrue(z1.isEquivalent(z2));

		z1 = Z.getInstance(UniformDistribution.getInstance(0, 5));
		z2 = Z.getInstance(UniformDistribution.getInstance(3, 5));
		assertTrue(z1.isEquivalent(z2));

		ZMod zmod = ZMod.getInstance(32);
		assertTrue(!z1.isEquivalent(zmod));
	}

}
