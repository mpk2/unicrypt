package ch.bfh.unicrypt.crypto.encoder.classes;

import ch.bfh.unicrypt.crypto.encoder.abstracts.AbstractEncoder;
import ch.bfh.unicrypt.crypto.encoder.interfaces.Encoder;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Set;
import ch.bfh.unicrypt.math.function.classes.CompositeFunction;
import ch.bfh.unicrypt.math.function.interfaces.Function;
import ch.bfh.unicrypt.math.helper.Compound;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CompositeEncoder
			 extends AbstractEncoder<Set, Set, Element, Element>
			 implements Compound<CompositeEncoder, Encoder> {

	private final Encoder[] encoders;

	protected CompositeEncoder(Encoder[] encoders) {
		this.encoders = encoders;
	}

	@Override
	public Encoder[] getAll() {
		return this.encoders.clone();
	}

	@Override
	public int getArity() {
		return this.encoders.length;
	}

	@Override
	public Encoder getAt(int index) {
		if (index < 0 || index >= this.getArity()) {
			throw new IndexOutOfBoundsException();
		}
		return this.encoders[index];
	}

	@Override
	public Encoder getAt(int... indices) {
		if (indices == null) {
			throw new IllegalArgumentException();
		}
		Encoder encoder = this;
		for (final int index : indices) {
			if (encoder instanceof CompositeEncoder) {
				encoder = ((CompositeEncoder) encoder).getAt(index);
			} else {
				throw new IllegalArgumentException();
			}
		}
		return encoder;
	}

	@Override
	public Encoder getFirst() {
		return this.getAt(0);
	}

	@Override
	public boolean isNull() {
		return this.getArity() == 0;
	}

	@Override
	public boolean isUniform() {
		return this.encoders.length <= 1;
	}

	@Override
	public Iterator<Encoder> iterator() {
		final CompositeEncoder encoder = this;
		return new Iterator<Encoder>() {
			int currentIndex = 0;

			@Override
			public boolean hasNext() {
				return this.currentIndex < encoder.getArity();
			}

			@Override
			public Encoder next() {
				if (this.hasNext()) {
					return encoder.getAt(this.currentIndex++);
				}
				throw new NoSuchElementException();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		};
	}

	@Override
	public CompositeEncoder removeAt(int index) {
		int arity = this.getArity();
		if (index < 0 || index >= arity) {
			throw new IndexOutOfBoundsException();
		}
		final Encoder[] remaining = new Encoder[arity - 1];
		for (int i = 0; i < arity - 1; i++) {
			if (i < index) {
				remaining[i] = this.getAt(i);
			} else {
				remaining[i] = this.getAt(i + 1);
			}
		}
		return CompositeEncoder.getInstance(remaining);
	}

	@Override
	protected Function abstractGetEncodingFunction() {
		int length = this.encoders.length;
		Function[] encodingFunctions = new Function[length];
		for (int i = 0; i < length; i++) {
			encodingFunctions[i] = this.encoders[i].getEncodingFunction();
		}
		return CompositeFunction.getInstance(encodingFunctions);
	}

	@Override
	protected Function abstractGetDecodingFunction() {
		int length = this.encoders.length;
		Function[] decodingFunctions = new Function[length];
		for (int i = 0; i < length; i++) {
			decodingFunctions[length - i - 1] = this.encoders[i].getDecodingFunction();
		}
		return CompositeFunction.getInstance(decodingFunctions);
	}

	public static CompositeEncoder getInstance(Encoder... encoders) {
		if (encoders == null || encoders.length == 0) {
			throw new IllegalArgumentException();
		}
		for (Encoder encoder : encoders) {
			if (encoder == null) {
				throw new IllegalArgumentException();
			}
		}
		return new CompositeEncoder(encoders);
	}

}
