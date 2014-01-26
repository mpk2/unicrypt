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
package ch.bfh.unicrypt.crypto.schemes.encryption.classes;

import ch.bfh.unicrypt.crypto.keygenerator.classes.FixedByteArrayKeyGenerator;
import ch.bfh.unicrypt.crypto.random.interfaces.RandomByteSequence;
import ch.bfh.unicrypt.crypto.schemes.encryption.abstracts.AbstractSymmetricEncryptionScheme;
import ch.bfh.unicrypt.math.algebra.concatenative.classes.ByteArrayElement;
import ch.bfh.unicrypt.math.algebra.concatenative.classes.ByteArrayMonoid;
import ch.bfh.unicrypt.math.algebra.general.classes.FiniteByteArrayElement;
import ch.bfh.unicrypt.math.algebra.general.classes.FixedByteArraySet;
import ch.bfh.unicrypt.math.algebra.general.classes.Pair;
import ch.bfh.unicrypt.math.algebra.general.classes.ProductSet;
import ch.bfh.unicrypt.math.function.abstracts.AbstractFunction;
import ch.bfh.unicrypt.math.function.interfaces.Function;
import ch.bfh.unicrypt.math.helper.ByteArray;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryptionScheme
	   extends AbstractSymmetricEncryptionScheme<ByteArrayMonoid, ByteArrayElement, ByteArrayMonoid, ByteArrayElement, FixedByteArraySet, FixedByteArrayKeyGenerator> {

	public enum KeyLength {

		KEY128(128), KEY192(192), KEY256(226);
		private final int length;

		private KeyLength(int length) {
			this.length = length;
		}

		public int getLenght() {
			return this.length;
		}

	}

	public enum Mode {

		CBC, ECB

	};

	public enum Padding {

		NONE("NoPadding"), PKCS5("PKCS5Padding");
		private final String name;

		private Padding(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return this.name;
		}

	};

	private static final String ALGORITHM_NAME = "AES";
	public static final KeyLength DEFAULT_KEY_LENGTH = KeyLength.KEY128;
	public static final Mode DEFAULT_MODE = Mode.CBC;
	public static final Padding DEFAULT_PADDING = Padding.PKCS5;

	public static final int AES_BLOCK_SIZE = 128;
	public static final ByteArrayMonoid AES_ENCRYPTION_SPACE = ByteArrayMonoid.getInstance(AES_BLOCK_SIZE / Byte.SIZE);
	public static final ByteArray DEFAULT_IV = ByteArray.getInstance(AES_BLOCK_SIZE / Byte.SIZE);

	private final KeyLength keyLength;
	private final Mode mode;
	private final Padding padding;
	private final ByteArray initializationVector;
	private Cipher cipher;

	protected AESEncryptionScheme(KeyLength keyLength, Mode mode, Padding padding, ByteArray initializationVector) {
		this.keyLength = keyLength;
		this.mode = mode;
		this.padding = padding;
		this.initializationVector = initializationVector;
		try {
			this.cipher = Cipher.getInstance(ALGORITHM_NAME + "/" + mode + "/" + padding);
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException();
		} catch (NoSuchPaddingException ex) {
			throw new RuntimeException();
		}
	}

	public KeyLength getKeyLength() {
		return this.keyLength;
	}

	public Mode getMode() {
		return this.mode;
	}

	public Padding getPadding() {
		return this.padding;
	}

	public ByteArray getInitializationVector() {
		return this.initializationVector;
	}

	@Override
	protected Function abstractGetEncryptionFunction() {
		ByteArrayMonoid messageSpace;
		if (this.padding == Padding.NONE) {
			messageSpace = ByteArrayMonoid.getInstance(AES_BLOCK_SIZE / Byte.SIZE);
		} else {
			messageSpace = ByteArrayMonoid.getInstance();
		}
		return new AESEncryptionFunction(messageSpace, this.getKeyGenerator().getKeySpace());
	}

	@Override
	protected Function abstractGetDecryptionFunction() {
		ByteArrayMonoid messageSpace;
		if (this.padding == Padding.NONE) {
			messageSpace = ByteArrayMonoid.getInstance(AES_BLOCK_SIZE / Byte.SIZE);
		} else {
			messageSpace = ByteArrayMonoid.getInstance();
		}
		return new AESDecryptionFunction(messageSpace, this.getKeyGenerator().getKeySpace());
	}

	@Override
	protected FixedByteArrayKeyGenerator abstractGetKeyGenerator() {
		return FixedByteArrayKeyGenerator.getInstance(this.getKeyLength().getLenght() / Byte.SIZE);
	}

	private class AESEncryptionFunction
		   extends AbstractFunction<ProductSet, Pair, ByteArrayMonoid, ByteArrayElement> {

		protected AESEncryptionFunction(ByteArrayMonoid messageSpace, FixedByteArraySet keySpace) {
			super(ProductSet.getInstance(keySpace, messageSpace), messageSpace);
		}

		@Override
		protected ByteArrayElement abstractApply(Pair element, RandomByteSequence randomByteSequence) {
			FiniteByteArrayElement key = (FiniteByteArrayElement) element.getFirst();
			ByteArrayElement message = (ByteArrayElement) element.getSecond();
			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getValue().getAll(), ALGORITHM_NAME);
			byte[] encryptedBytes;
			try {
				if (mode == Mode.CBC) {
					cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(initializationVector.getAll()));
				} else {
					cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
				}
				encryptedBytes = cipher.doFinal(message.getValue().getAll());
			} catch (InvalidKeyException ex) {
				throw new RuntimeException();
			} catch (IllegalBlockSizeException ex) {
				throw new RuntimeException();
			} catch (BadPaddingException ex) {
				throw new RuntimeException();
			} catch (InvalidAlgorithmParameterException ex) {
				throw new RuntimeException();
			}
			return this.getCoDomain().getElement(encryptedBytes);
		}

	}

	private class AESDecryptionFunction
		   extends AbstractFunction<ProductSet, Pair, ByteArrayMonoid, ByteArrayElement> {

		protected AESDecryptionFunction(ByteArrayMonoid messageSpace, FixedByteArraySet keySpace) {
			super(ProductSet.getInstance(keySpace, messageSpace), messageSpace);
		}

		@Override
		protected ByteArrayElement abstractApply(Pair element, RandomByteSequence randomByteSequence) {
			FiniteByteArrayElement key = (FiniteByteArrayElement) element.getFirst();
			ByteArrayElement encryption = (ByteArrayElement) element.getSecond();
			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getValue().getAll(), ALGORITHM_NAME);
			byte[] message;
			try {
				if (mode == Mode.CBC) {
					cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(initializationVector.getAll()));
				} else {
					cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
				}
				message = cipher.doFinal(encryption.getValue().getAll());
			} catch (InvalidKeyException e) {
				throw new RuntimeException();
			} catch (IllegalBlockSizeException e) {
				throw new RuntimeException();
			} catch (BadPaddingException e) {
				throw new RuntimeException();
			} catch (InvalidAlgorithmParameterException ex) {
				throw new RuntimeException();
			}
			return this.getCoDomain().getElement(message);
		}

	}

	public static AESEncryptionScheme getInstance() {
		return AESEncryptionScheme.getInstance(AESEncryptionScheme.DEFAULT_KEY_LENGTH, AESEncryptionScheme.DEFAULT_MODE, AESEncryptionScheme.DEFAULT_PADDING, AESEncryptionScheme.DEFAULT_IV);
	}

	public static AESEncryptionScheme getInstance(KeyLength keyLength, Mode mode, Padding padding, ByteArray initializationVector) {
		if (keyLength == null || mode == null || padding == null || initializationVector == null || initializationVector.getLength() != AES_BLOCK_SIZE / Byte.SIZE) {
			throw new IllegalArgumentException();
		}
		return new AESEncryptionScheme(keyLength, mode, padding, initializationVector);
	}

}
