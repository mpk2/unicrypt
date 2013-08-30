package ch.bfh.unicrypt.crypto.encoding.interfaces;

import ch.bfh.unicrypt.math.element.Element;
import ch.bfh.unicrypt.math.function.interfaces.Function;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Group;

public interface EncodingScheme {

  public Element encode(Element element);

  public Element decode(Element element);

  public Function getEncodingFunction();

  public Function getDecodingFunction();

  public Group getMessageSpace();

  public Group getEncodingSpace();

}
