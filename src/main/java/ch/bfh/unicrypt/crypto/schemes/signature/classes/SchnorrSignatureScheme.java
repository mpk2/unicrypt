package ch.bfh.unicrypt.crypto.schemes.signature.classes;

import ch.bfh.unicrypt.crypto.keygenerator.classes.SchnorrSignatureKeyPairGenerator;
import ch.bfh.unicrypt.crypto.keygenerator.interfaces.KeyPairGenerator;
import ch.bfh.unicrypt.crypto.schemes.signature.abstracts.AbstractRandomizedSignatureScheme;
import ch.bfh.unicrypt.math.algebra.general.interfaces.CyclicGroup;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Set;
import ch.bfh.unicrypt.math.function.interfaces.Function;

public class SchnorrSignatureScheme<MS extends CyclicGroup, ME extends Element>
	   extends AbstractRandomizedSignatureScheme<Set, Set, Set, Element, Element> {

	private final MS cyclicGroup;
	private final ME generator;

	protected SchnorrSignatureScheme(MS cyclicGroup, ME generator) {
		this.cyclicGroup = cyclicGroup;
		this.generator = generator;
	}

	@Override
	protected KeyPairGenerator abstractGetKeyPairGenerator() {
		return SchnorrSignatureKeyPairGenerator.getInstance(this.getGenerator());
	}

	@Override
	public Function abstractGetSignatureFunction() {

//		Element g = this.G_q.getElement(4);
//		Element k = this.G_q.getZModOrder().getElement(3);
//		Element x = this.G_q.getZModOrder().getElement(5);
//		StringElement m = StringMonoid.getInstance(Alphabet.BASE64).getElement("MessageXX");
//
//		// f(k) = g^k
//		Function f1 = GeneratorFunction.getInstance(g);
//		Element r = f1.apply(k);
//		System.out.println(r);
//
//		// f(m,r) = H(m||r)
//		Function f2 = CompositeFunction.getInstance(
//			   ApplyFunction.getInstance(m.getSet()),
//			   HashFunction.getInstance(m.getSet()));
//		//Element e = f2.apply(Tuple.getInstance(m, r));
//
//		FiniteByteArrayElement hash = Tuple.getInstance(m, r).getHashValue();
//		Element e = ModuloFunction.getInstance(hash.getSet(), this.G_q.getZModOrder()).apply(hash);
//		System.out.println(e);
//
//		// f(k, x, e) = k - xe
//		ProductSet f4Domain = ProductSet.getInstance(G_q.getZModOrder(), 3);
//		Function f4 = CompositeFunction.getInstance(
//			   MultiIdentityFunction.getInstance(f4Domain, 2),
//			   ProductFunction.getInstance(
//			   SelectionFunction.getInstance(f4Domain, 0),
//			   CompositeFunction.getInstance(
//			   AdapterFunction.getInstance(f4Domain, 1, 2),
//			   CompositeFunction.getInstance(
//			   SelfApplyFunction.getInstance((SemiGroup) x.getSet()),
//			   InvertFunction.getInstance(G_q.getZModOrder())))),
//			   ApplyFunction.getInstance(G_q.getZModOrder()));
//
//		Element s = f4.apply(Tuple.getInstance(k, x, e));
//		System.out.println(s);



//			Function f4 = CompositeFunction.getInstance(
//			   MultiIdentityFunction.getInstance(f4Domain, 2),
//			   ProductFunction.getInstance(
//					SelectionFunction.getInstance(f4Domain, 0),
//					CompositeFunction.getInstance(
//						AdapterFunction.getInstance(f4Domain, 1,2),
//						CompositeFunction.getInstance(
//							SelfApplyFunction.getInstance((SemiGroup) x.getSet()),
//							InvertFunction.getInstance(G_q.getZModOrder())
//						)
//					)
//			   ),
//			   ApplyFunction.getInstance(G_q.getZModOrder())
//			   );






		return null;
	}

	@Override
	public Function abstractGetVerificationFunction() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public final MS getCyclicGroup() {
		return this.cyclicGroup;
	}

	public final ME getGenerator() {
		return this.generator;
	}
//import java.util.Random;
//
//import ch.bfh.unicrypt.crypto.keygenerator.old.DDHGroupKeyPairGeneratorClass;
//import ch.bfh.unicrypt.crypto.schemes.signature.abstracts.AbstractRandomizedSignatureScheme;
//import ch.bfh.unicrypt.math.algebra.additive.classes.ZPlusMod;
//import ch.bfh.unicrypt.math.algebra.general.classes.ProductGroup;
//import ch.bfh.unicrypt.math.algebra.general.classes.Tuple;
//import ch.bfh.unicrypt.math.algebra.general.interfaces.DDHGroup;
//import ch.bfh.unicrypt.math.function.classes.HashFunction;
//import ch.bfh.unicrypt.math.function.interfaces.Function;
//
//public class SchnorrSignatureScheme extends AbstractRandomizedSignatureScheme {
//
//  public static final HashFunction.HashAlgorithm DEFAULT_HASH_ALGORITHM = HashFunction.HashAlgorithm.SHA256;
//  public static final ConcatenateFunction.ConcatParameter DEFAULT_CONCAT_ALGORITHM = ConcatParameter.Plain;
//  public static final Mapper DEFAULT_MAPPER = new CharsetXRadixYMapperClass(CharsetXRadixYMapperClass.DEFAULT_CHARSET, CharsetXRadixYMapperClass.DEFAULT_RADIX);
//  private final ZPlusMod zPlusMod;
//  private final DDHGroup ddhGroup;
//  private final AtomicElement generator; // of ddhGroup
//
//  private final DDHGroupKeyPairGenerator keyGenerator;
//  private final Function signatureFunction;
//  private final Function verificationFunction;
//  private final HashFunction hashFunction;
//  private final ConcatenateFunction concatFunction;
//
//  public SchnorrSignatureScheme(final DDHGroup ddhGroup) {
//    this(ddhGroup, SchnorrSignatureScheme.DEFAULT_HASH_ALGORITHM, SchnorrSignatureScheme.DEFAULT_CONCAT_ALGORITHM, SchnorrSignatureScheme.DEFAULT_MAPPER);
//  }
//
//  public SchnorrSignatureScheme(final DDHGroup ddhGroup, final AtomicElement generator) {
//    this(ddhGroup,generator, SchnorrSignatureScheme.DEFAULT_HASH_ALGORITHM, SchnorrSignatureScheme.DEFAULT_CONCAT_ALGORITHM, SchnorrSignatureScheme.DEFAULT_MAPPER);
//  }
//
//  public SchnorrSignatureScheme(final DDHGroup ddhGroup, final HashAlgorithm hashAlgorithm, final ConcatParameter concatParameter, final Mapper mapper) {
//    this(ddhGroup, ddhGroup.getDefaultGenerator(), hashAlgorithm, concatParameter, mapper);
//  }
//
//  public SchnorrSignatureScheme(final DDHGroup ddhGroup,
//      final AtomicElement generator,
//      final HashAlgorithm hashAlgorithm,
//      final ConcatParameter concatParameter,
//      final Mapper mapper) {
//    if ((ddhGroup == null) || (generator == null) || !ddhGroup.contains(generator)) {
//      throw new IllegalArgumentException();
//    }
//    this.zPlusMod = ddhGroup.getZModOrder();
//    this.ddhGroup = ddhGroup;
//    this.generator = generator;
//
//    this.hashFunction = new HashFunction(hashAlgorithm, this.zPlusMod);
//    final ProductGroup concatGroup = new ProductGroup(this.getMessageSpace(), this.ddhGroup);
//    this.concatFunction = new ConcatenateFunction(concatGroup, concatParameter, mapper);
//    this.keyGenerator = new DDHGroupKeyPairGeneratorClass(ddhGroup, generator);
//    this.signatureFunction = this.createSignatureFunction();
//    this.verificationFunction = this.createVerificationFunction();
//  }
//
//  @Override
//  public Tuple sign(final Element privateKey, final Element message, final Element randomization) {
//    return (Tuple) super.sign(privateKey, message, randomization);
//  }
//
//  @Override
//  public DDHGroupKeyPairGenerator getKeyPairGenerator() {
//    return this.keyGenerator;
//  }
//
//  @Override
//  public Function getSignatureFunction() {
//    return this.signatureFunction;
//  }
//
//  @Override
//  public Function getVerificationFunction() {
//    return this.verificationFunction;
//  }
//
//  @Override
//  public HashFunction getHashFunction() {
//    return this.hashFunction;
//  }
//
//  @Override
//  public ProductGroup getSignatureSpace() {
//    return (ProductGroup) super.getSignatureSpace();
//  }
//
//  @Override
//  public ZPlusMod getRandomizationSpace() {
//    return this.zPlusMod;
//  }
//
//  private Function createSignatureFunction() {
//    return new SignatureFunctionClass(this.zPlusMod, this.generator, this.hashFunction, this.concatFunction);
//  }
//
//  // private Function createSignatureFunctionLeft(final ProductGroup domain) {
//  // return new CompositeFunctionClass(
//  // new MultiIdentityFunctionClass(domain, 2),
//  // new ProductFunctionClass(
//  // new SelectiveIdentityFunctionClass(1),
//  // new CompositeFunctionClass(
//  // new SelectiveIdentityFunctionClass(2),
//  // new PartiallyAppliedFunctionClass(new ApplyFunctionClass(), this.generator,
//  // 0)
//  //
//  // ;
//  // }
//
//  private Function createVerificationFunction() {
//    return new VerificationFunctionClass(this.zPlusMod, this.ddhGroup, this.generator, this.hashFunction, this.concatFunction);
//  }
//
//  private static class SignatureFunctionClass extends ProductDomainFunctionAbstract {
//
//    private final Element generator;
//    private final HashFunction hashFunction;
//    private final ConcatenateFunction concatFunction;
//
//    public SignatureFunctionClass(final ZPlusMod zPlusMod, final Element generator, final HashFunction hashFunction, final ConcatenateFunction concatFunction) {
//      super(SignatureFunctionClass.createDomain(zPlusMod), SignatureFunctionClass.createCoDomain(zPlusMod));
//      this.generator = generator;
//      this.hashFunction = hashFunction;
//      this.concatFunction = concatFunction;
//    }
//
//    @Override
//    public ProductGroup getCoDomain() {
//      return (ProductGroup) super.getCoDomain();
//    }
//
//    @Override
//    public Element apply(final Element element, final Random random) {
//      final Tuple tuple = (Tuple) element;
//      final AtomicElement privateKey = (AtomicElement) tuple.getElementAt(0);
//      final Element message = tuple.getElementAt(1);
//      final AtomicElement randomization = (AtomicElement) tuple.getElementAt(2);
//      final AtomicElement concatElement = this.concatFunction.apply(message, this.generator.selfApply(randomization));
//      final Element left = this.hashFunction.apply(concatElement);
//      final Element right = randomization.apply(left.selfApply(privateKey).invert());
//      return this.getCoDomain().getElement(left, right);
//    }
//
//    private static ProductGroup createDomain(final ZPlusMod zPlusMod) {
//      return new ProductGroup(zPlusMod, ZPlus.getInstance(), zPlusMod);
//    }
//
//    private static PowerGroup createCoDomain(final ZPlusMod zPlusMod) {
//      return new PowerGroup(zPlusMod, 2);
//    }
//
//  }
//
//  private static class VerificationFunctionClass extends ProductDomainFunctionAbstract {
//
//    private final Element generator;
//    private final HashFunction hashFunction;
//    private final ConcatenateFunction concatFunction;
//
//    public VerificationFunctionClass(final ZPlusMod zPlusMod,
//        final DDHGroup ddhGroup,
//        final Element generator,
//        final HashFunction hashFunction,
//        final ConcatenateFunction concatFunciton) {
//      super(VerificationFunctionClass.createDomain(zPlusMod, ddhGroup), BooleanGroup.getInstance());
//      this.generator = generator;
//      this.hashFunction = hashFunction;
//      this.concatFunction = concatFunciton;
//    }
//
//    @Override
//    public BooleanGroup getCoDomain() {
//      return (BooleanGroup) super.getCoDomain();
//    }
//
//    @Override
//    public Element apply(final Element element, final Random random) {
//      final Tuple tuple = (Tuple) element;
//      final Element publicKey = tuple.getElementAt(0);
//      final Element message = tuple.getElementAt(1);
//      final AtomicElement left = (AtomicElement) tuple.getElementAt(2, 0);
//      final AtomicElement right = (AtomicElement) tuple.getElementAt(2, 1);
//      final AtomicElement concatElement = this.concatFunction.apply(message, this.generator.selfApply(right).apply(publicKey.selfApply(left)));
//      final Element result = this.hashFunction.apply(concatElement);
//      return this.getCoDomain().getElement(left.equals(result));
//    }
//
//    private static ProductGroup createDomain(final ZPlusMod zPlusMod, final DDHGroup ddhGroup) {
//      return new ProductGroup(ddhGroup, ZPlus.getInstance(), new PowerGroup(zPlusMod, 2));
//    }
//
//  }
}
