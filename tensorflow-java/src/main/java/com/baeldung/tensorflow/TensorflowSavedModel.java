package com.baeldung.tensorflow;

import org.tensorflow.SavedModelBundle;
import org.tensorflow.Tensor;

public class TensorflowSavedModel {

	public static void main(String[] args) {
		SavedModelBundle model = SavedModelBundle.load("./model", "serve");
		Tensor<Integer> tensor = model.session().runner().fetch("z").feed("x", Tensor.<Integer>create(3, Integer.class))
				.feed("y", Tensor.<Integer>create(3, Integer.class)).run().get(0).expect(Integer.class);
		System.out.println(tensor.intValue());
	}
}