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
package ch.bfh.unicrypt.crypto.random.distributionsampler.classes;

import ch.bfh.unicrypt.crypto.random.distributionsampler.classes.SecureRandomSampler;
import ch.bfh.unicrypt.crypto.random.distributionsampler.interfaces.DistributionSampler;
import ch.bfh.unicrypt.crypto.random.interfaces.TrueRandomByteSequence;
import ch.bfh.unicrypt.math.helper.ByteArray;

/**
 * This class shows the collection of a sample distribution in order to find a seed with many bytes. Entropy is not
 * guaranteed.
 * <p>
 * Please note, that this class needs to be split in two parts (future work): The DistributionSecureRandomSamplerr
 * ,Distribution(s).
 * <p>
 * This will lead to the aggregation: A distributionSecureRandomSamplerr may hold one or more Distributions
 * <p>
 * <p>
 * <p>
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class DistributionSamplerCollector {

	private int securityParameterInBytes;
	private Thread collectorThread; //Should be a threadpool that will be provided to the different DistributionSamplers... one day...
	private DistributionSampler dataCollector; //Should be al list of distributionSamplers... one day...
	private TrueRandomByteSequence sink;

	protected DistributionSamplerCollector(TrueRandomByteSequence sink) {
		if (sink == null) {
			throw new IllegalArgumentException();
		}
		this.sink = sink;
		this.dataCollector = new SecureRandomSampler(this);
		collectorThread = new Thread(dataCollector);
		collectorThread.start();
	}

	public static DistributionSamplerCollector getInstance(TrueRandomByteSequence sink) {
		return new DistributionSamplerCollector(sink);

	}

	public int getSecurityParameterInBytes() {
		return this.securityParameterInBytes;
	}

	/**
	 * Blocking method used to seed a TrueRandomByteSequence instance. Shall return a random looking amount of bytes,
	 * created outside the context of UniCrypt...
	 * <p>
	 * @param amountOfBytes
	 * @return ByteArrayElement containing the desired amount of 'random' bytes.
	 */
	public ByteArray getDistributionSample(int amountOfBytes) {
		//This will collect all data received from the different distributionSamplers... one day...
		if (amountOfBytes < 1) {
			throw new IllegalArgumentException();
		}
		return dataCollector.getDistributionSample(amountOfBytes);
	}

	public boolean isCollecting() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public void setCollectionStatus(boolean isCollecting) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public void setFreshSamples(ByteArray samples) {
		//This will collect all data received from the different distributionSamplers... one day...
		this.sink.setFreshData(samples);
	}

}
