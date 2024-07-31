/*
 * Copyright 2002-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.directory.server.core.avltree;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Comparator;
import org.apache.directory.shared.ldap.util.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to serialize the Array data.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev$, $Date$
 */
@SuppressWarnings("unchecked")
public class ArrayMarshaller<E> implements Marshaller<ArrayTree<E>> {

	/** static logger */
	private static final Logger LOG = LoggerFactory.getLogger(ArrayMarshaller.class);

	/** used for serialized form of an empty AvlTree */
	private static final byte[] EMPTY_TREE = new byte[1];

	/** marshaller to be used for marshalling the keys */
	private Marshaller<E> keyMarshaller;

	/** key Comparator for the AvlTree */
	private Comparator<E> comparator;

	/**
	 * Creates a new instance of AvlTreeMarshaller with a custom key Marshaller.
	 * @param comparator Comparator to be used for key comparision
	 * @param keyMarshaller marshaller for keys
	 */
	public ArrayMarshaller(Comparator<E> comparator, Marshaller<E> keyMarshaller) {
		this.comparator = comparator;
		this.keyMarshaller = keyMarshaller;
	}

	/**
	 * Creates a new instance of AvlTreeMarshaller with the default key Marshaller which
	 * uses Java Serialization.
	 * @param comparator Comparator to be used for key comparision
	 */
	public ArrayMarshaller(Comparator<E> comparator) {
		this.comparator = comparator;
		this.keyMarshaller = (Marshaller<E>) DefaultMarshaller.INSTANCE;
	}

	/**
	 * Marshals the given tree to bytes
	 * @param tree the tree to be marshalled
	 */
	public byte[] serialize(ArrayTree<E> tree) {
		if ((tree == null) || (tree.size() == 0)) {
			return EMPTY_TREE;
		}

		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(byteStream);
		byte[] data = null;

		try {
			out.writeByte(0); // represents the start of an Array byte stream
			out.writeInt(tree.size());

			for (int position = 0; position < tree.size(); position++) {
				E value = tree.get(position);
				byte[] bytes = this.keyMarshaller.serialize(value);

				// Write the key length
				out.writeInt(bytes.length);

				// Write the key if its length is not null
				if (bytes.length != 0) {
					out.write(bytes);
				}
			}

			out.flush();
			data = byteStream.toByteArray();

			// Try to deserialize, just to see
			try {
				deserialize(data);
			}
			catch (NullPointerException npe) {
				System.out.println("Bad serialization, tree : [" + StringTools.dumpBytes(data) + "]");
				throw npe;
			}

			out.close();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}

		return data;
	}

	/**
	 * Creates an Array from given bytes of data.
	 * @param data byte array to be converted into an array
	 */
	public ArrayTree<E> deserialize(byte[] data) throws IOException {
		try {
			if ((data == null) || (data.length == 0)) {
				throw new IOException("Null or empty data array is invalid.");
			}

			if ((data.length == 1) && (data[0] == 0)) {
				E[] array = (E[]) new Object[] {};
				ArrayTree<E> tree = new ArrayTree<E>(this.comparator, array);
				return tree;
			}

			ByteArrayInputStream bin = new ByteArrayInputStream(data);
			DataInputStream din = new DataInputStream(bin);

			byte startByte = din.readByte();

			if (startByte != 0) {
				throw new IOException("wrong array serialized data format");
			}

			int size = din.readInt();
			E[] nodes = (E[]) new Object[size];

			for (int i = 0; i < size; i++) {
				// Read the object's size
				int dataSize = din.readInt();

				if (dataSize != 0) {
					byte[] bytes = new byte[dataSize];

					din.read(bytes);
					E key = this.keyMarshaller.deserialize(bytes);
					nodes[i] = key;
				}
			}

			ArrayTree<E> arrayTree = new ArrayTree<E>(this.comparator, nodes);

			return arrayTree;
		}
		catch (NullPointerException npe) {
			System.out.println("Bad tree : [" + StringTools.dumpBytes(data) + "]");
			throw npe;
		}
	}

}