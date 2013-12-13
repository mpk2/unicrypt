/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bfh.unicrypt.math.algebra.general.classes;

import ch.bfh.unicrypt.math.algebra.general.abstracts.AbstractElement;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Set;
import ch.bfh.unicrypt.math.helper.Compound;
import ch.bfh.unicrypt.math.helper.CompoundIterator;
import ch.bfh.unicrypt.math.utility.MathUtil;
import java.math.BigInteger;
import java.util.Iterator;

/**
 *
 * @author rolfhaenni
 */
public class Tuple
			 extends AbstractElement<ProductSet, Tuple>
			 implements Compound<Tuple, Element>, Iterable<Element> {

	private final Element[] elements;
	private final int arity;

	protected Tuple(final ProductSet set, final Element[] elements) {
		super(set);
		this.elements = elements.clone();
		this.arity = elements.length;
	}

	@Override
	protected BigInteger standardGetValue() {
		int arity = this.getArity();
		BigInteger[] values = new BigInteger[arity];
		for (int i = 0; i < arity; i++) {
			values[i] = this.elements[i].getValue();
		}
		return MathUtil.foldAndPair(values);
	}

	@Override
	public int getArity() {
		return this.arity;
	}

	@Override
	public final boolean isNull() {
		return this.getArity() == 0;
	}

	@Override
	public final boolean isUniform() {
		return this.elements.length <= 1;
	}

	@Override
	public Element getFirst() {
		return this.getAt(0);

	}

	@Override
	public Element getAt(int index) {
		if (index < 0 || index >= this.getArity()) {
			throw new IndexOutOfBoundsException();
		}
		if (this.isUniform()) {
			return this.elements[0];
		}
		return this.elements[index];
	}

	@Override
	public Element getAt(int... indices) {
		if (indices == null) {
			throw new IllegalArgumentException();
		}
		Element element = this;
		for (final int index : indices) {
			if (element.isTuple()) {
				element = ((Tuple) element).getAt(index);
			} else {
				throw new IllegalArgumentException();
			}
		}
		return element;
	}

	@Override
	public Element[] getAll() {
		return this.elements.clone();
	}

	@Override
	public Tuple removeAt(final int index) {
		int arity = this.getArity();
		if (index < 0 || index >= arity) {
			throw new IndexOutOfBoundsException();
		}
		final Element[] remainingElements = new Element[arity - 1];
		for (int i = 0; i < arity - 1; i++) {
			if (i < index) {
				remainingElements[i] = this.getAt(i);
			} else {
				remainingElements[i] = this.getAt(i + 1);
			}
		}
		return this.getSet().removeAt(index).getElement(remainingElements);
	}

	@Override
	public Iterator<Element> iterator() {
		return new CompoundIterator<Element>(this);
	}

	@Override
	protected boolean standardIsEqual(Tuple element) {
		for (int i = 0; i < this.getArity(); i++) {
			if (!this.getAt(i).isEqual(element.getAt(i))) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected String standardToStringName() {
		return this.getSet().getClass().getSimpleName() + "Tuple";
	}

	@Override
	protected String standardToStringContent() {
		String result = "";
		String separator = "";
		for (Element element : this) {
			result = result + separator + element.toString();
			separator = ", ";
		}
		return result;
	}

	/**
	 * This is a static factory method to construct a composed element without the need of constructing the corresponding
	 * product or power group beforehand. The input elements are given as an array.
	 * <p>
	 * <p/>
	 * @param elements The array of input elements
	 * @return The corresponding tuple element
	 * @throws IllegalArgumentException if {@literal elements} is null or contains null
	 */
	public static Tuple getInstance(Element... elements) {
		if (elements == null) {
			throw new IllegalArgumentException();
		}
		int arity = elements.length;
		final Set[] sets = new Set[arity];

		for (int i = 0; i < arity; i++) {
			if (elements[i] == null) {
				throw new IllegalArgumentException();
			}
			sets[i] = elements[i].getSet();
		}
		if (arity == 2) {
			return new Pair(ProductSet.getInstance(sets), elements);
		}
		if (arity == 3) {
			return new Triple(ProductSet.getInstance(sets), elements);
		}
		return new Tuple(ProductSet.getInstance(sets), elements);
	}

}
