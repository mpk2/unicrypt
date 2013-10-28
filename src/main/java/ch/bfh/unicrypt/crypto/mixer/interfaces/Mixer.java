package ch.bfh.unicrypt.crypto.mixer.interfaces;

import java.util.List;
import java.util.Random;

import ch.bfh.unicrypt.math.algebra.concatenative.classes.PermutationElement;
import ch.bfh.unicrypt.math.algebra.concatenative.classes.PermutationGroup;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Group;

public interface Mixer {

  public List<Element> shuffle(List<Element> elements);

  public List<Element> shuffle(List<Element> elements, Random random);

  public List<Element> shuffle(List<Element> elements, PermutationElement permutation);

  public PermutationGroup getPermutationSpace(int size);

  public Group getGroup();

}