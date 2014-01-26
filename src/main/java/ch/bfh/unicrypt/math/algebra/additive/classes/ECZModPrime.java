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
package ch.bfh.unicrypt.math.algebra.additive.classes;

import ch.bfh.unicrypt.crypto.random.interfaces.RandomByteSequence;
import ch.bfh.unicrypt.math.algebra.additive.abstracts.AbstractEC;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZModElement;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZModPrime;
import ch.bfh.unicrypt.math.helper.Point;
import ch.bfh.unicrypt.math.utility.MathUtil;
import java.math.BigInteger;

public class ECZModPrime
	   extends AbstractEC<ECZModPrimeElement, ZModPrime, ZModElement> {

	protected ECZModPrime(ZModPrime finiteField, ZModElement a, ZModElement b, ZModElement gx, ZModElement gy, BigInteger givenOrder, BigInteger coFactor) {
		super(finiteField, a, b, gx, gy, givenOrder, coFactor);
		// this test should be moved to getInstance
		if (!isValid()) {
			throw new IllegalArgumentException("Curve parameters are not valid");
		}
	}

	protected ECZModPrime(ZModPrime finiteField, ZModElement a, ZModElement b, BigInteger givenOrder, BigInteger coFactor) {
		super(finiteField, a, b, givenOrder, coFactor);
		// this test should be moved to getInstance
		if (!isValid()) {
			throw new IllegalArgumentException("Curve parameters are not valid");
		}
	}

	@Override
	public boolean abstractContains(ZModElement x) {
		BigInteger p = this.getFiniteField().getModulus();
		ZModElement right = x.power(3).add(getA().multiply(x)).add(getB());
		if (MathUtil.hasSqrtModPrime(right.getValue(), p)) {
			BigInteger y1 = MathUtil.sqrtModPrime(right.getValue(), p);
			ZModElement y = this.getFiniteField().getElement(y1);
			return this.abstractContains(x, y);
		} else {
			return false;
		}
	}

	@Override
	protected boolean abstractContains(ZModElement x, ZModElement y) {
		ZModElement left = y.power(2);
		ZModElement right = x.power(3).add(x.multiply(this.getA())).add(this.getB());
		return left.isEquivalent(right);
	}

	@Override
	protected ECZModPrimeElement abstractGetElement(Point<ZModElement> value) {
		return new ECZModPrimeElement(this, value);
	}

	@Override
	protected ECZModPrimeElement abstractGetIdentityElement() {
		return new ECZModPrimeElement(this);
	}

	@Override
	protected ECZModPrimeElement abstractApply(ECZModPrimeElement element1, ECZModPrimeElement element2) {
		if (element1.isZero()) {
			return element2;
		}
		if (element2.isZero()) {
			return element1;
		}
		if (element1.isEquivalent(element2.invert())) {
			return this.getZeroElement();
		}
		ZModElement s, rx, ry;
		ZModElement px = element1.getX();
		ZModElement py = element1.getY();
		ZModElement qx = element2.getX();
		ZModElement qy = element2.getY();
		if (element1.isEquivalent(element2)) {
			ZModElement three = (ZModElement) this.getFiniteField().getElement(3);
			ZModElement two = (ZModElement) this.getFiniteField().getElement(2);
			s = ((px.power(2).multiply(three)).apply(this.getA())).divide(py.multiply(two));
			rx = s.power(2).apply(px.multiply(two).invert());
			ry = s.multiply(px.subtract(rx)).apply(py.invert());
		} else {
			s = py.apply(qy.invert()).divide(px.apply(qx.invert()));
			rx = (s.power(2).apply(px.invert()).apply(qx.invert()));
			ry = py.invert().add(s.multiply(px.apply(rx.invert())));
		}
		return this.abstractGetElement(Point.getInstance(rx, ry));
	}

	@Override
	protected ECZModPrimeElement abstractInvert(ECZModPrimeElement element) {
		if (element.isZero()) {
			return this.getZeroElement();
		}
		return this.abstractGetElement(Point.getInstance(element.getX(), element.getY().invert()));
	}

	@Override
	protected ECZModPrimeElement getRandomElementWithoutGenerator(RandomByteSequence randomByteSequence) {
		BigInteger p = this.getFiniteField().getModulus();
		ZModElement x = (ZModElement) this.getFiniteField().getRandomElement(randomByteSequence);
		ZModElement y = x.power(3).add(this.getA().multiply(x)).add(this.getB());
		boolean neg = x.getValue().mod(new BigInteger("2")).equals(BigInteger.ONE);

		while (!MathUtil.hasSqrtModPrime(y.getValue(), p)) {
			x = (ZModElement) this.getFiniteField().getRandomElement(randomByteSequence);
			y = x.power(3).add(this.getA().multiply(x)).add(this.getB());
		}
		//if neg is true return solution 2(p-sqrt) of sqrtModPrime else solution 1
		if (neg) {
			y = (ZModElement) this.getFiniteField().getElement(p.subtract(MathUtil.sqrtModPrime(y.getValue(), p)));
		} else {
			y = (ZModElement) this.getFiniteField().getElement(MathUtil.sqrtModPrime(y.getValue(), p));
		}
		return this.abstractGetElement(Point.getInstance(x, y));
	}

	private boolean isValid() {
		boolean c1, c2, c3, c4, c5, c61, c62;
		ZModElement i4 = (ZModElement) getFiniteField().getElement(4);
		ZModElement i27 = (ZModElement) getFiniteField().getElement(27);
		c1 = !getA().power(3).multiply(i4).add(i27.multiply(getB().power(2))).isZero();
		c2 = contains(this.getDefaultGenerator());
		c3 = MathUtil.arePrime(getOrder());
		c4 = 0 >= getCoFactor().compareTo(new BigInteger("4"));
		c5 = getZeroElement().equals(getDefaultGenerator().selfApply(getOrder()));
		c61 = true; //_> Must be corrected!
		for (int i = 1; i < 20; i++) {
			// ???
		}
		c62 = !getOrder().multiply(getCoFactor()).equals(this.getFiniteField().getModulus());
		return c1 && c2 && c3 && c4 && c5 && c61 && c62;
	}

	/**
	 * Returns an elliptic curve over Fp y²=x³+ax+b
	 * <p>
	 * @param f          Finite field of type ZModPrime
	 * @param a          Element of F_p representing a in the curve equation
	 * @param b          Element of F_p representing b in the curve equation
	 * @param givenOrder Order of the the used subgroup
	 * @param coFactor   Co-factor h*order= N -> total order of the group
	 * @return
	 */
	public static ECZModPrime getInstance(ZModPrime f, ZModElement a, ZModElement b, BigInteger givenOrder, BigInteger coFactor) {
		// isValid() test should come here!!
		return new ECZModPrime(f, a, b, givenOrder, coFactor);
	}

	/**
	 * Returns an elliptic curve over Fp y²=x³+ax+b
	 * <p>
	 * @param f          Finite field of type ZModPrime
	 * @param a          Element of F_p representing a in the curve equation
	 * @param b          Element of F_p representing b in the curve equation
	 * @param gx         x-coordinate of the generator
	 * @param gy         y-coordinate of the generator
	 * @param givenOrder Order of the the used subgroup
	 * @param coFactor   Co-factor h*order= N -> total order of the group
	 * @return
	 */
	public static ECZModPrime getInstance(ZModPrime f, ZModElement a, ZModElement b, ZModElement gx, ZModElement gy, BigInteger givenOrder, BigInteger coFactor) {
		// isValid() test should come here!!
		return new ECZModPrime(f, a, b, gx, gy, givenOrder, coFactor);
	}

}
