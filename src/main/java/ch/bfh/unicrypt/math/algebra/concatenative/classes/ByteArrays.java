/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bfh.unicrypt.math.algebra.concatenative.classes;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

import ch.bfh.unicrypt.math.algebra.concatenative.abstracts.AbstractConcatenativeMonoid;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Set;

/**
 *
 * @author rolfhaenni
 */
public class ByteArrays extends AbstractConcatenativeMonoid<ByteArrayElement> {

  private ByteArrays() {
  }

  public final ByteArrayElement getElement(final byte[] bytes) {
    if (bytes == null) {
      throw new IllegalArgumentException();
    }
    return this.standardGetElement(bytes);
  }

  protected ByteArrayElement standardGetElement(byte[] bytes) {
    return new ByteArrayElement(this, bytes) {
    };
  }

  @Override
  protected ByteArrayElement abstractGetElement(BigInteger value) {
    return this.standardGetElement(value.toByteArray());
  }

  //
  // The following protected methods implement the abstract methods from
  // various super-classes
  //
  @Override
  protected ByteArrayElement abstractGetIdentityElement() {
    return this.standardGetElement(new byte[]{});
  }

  @Override
  protected ByteArrayElement abstractApply(Element element1, Element element2) {
    byte[] bytes1 = ((ByteArrayElement) element1).getBytes();
    byte[] bytes2 = ((ByteArrayElement) element2).getBytes();
    byte[] result = Arrays.copyOf(bytes1, bytes1.length + bytes2.length);
    System.arraycopy(bytes2, 0, result, bytes2.length, bytes2.length);
    return this.standardGetElement(result);
  }

  @Override
  protected BigInteger abstractGetOrder() {
    return Set.INFINITE_ORDER;
  }

  @Override
  protected ByteArrayElement abstractGetRandomElement(Random random) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected boolean abstractContains(BigInteger value) {
    return value.signum() >= 0;
  }
  //
  // STATIC FACTORY METHODS
  //
  private static ByteArrays instance;

  /**
   * Returns the singleton object of this class.
   *
   * @return The singleton object of this class
   */
  public static ByteArrays getInstance() {
    if (ByteArrays.instance == null) {
      ByteArrays.instance = new ByteArrays();
    }
    return ByteArrays.instance;
  }

}