package com.baeldung.object.copy.factory;

import java.util.Map;
import java.util.TreeMap;

import com.baeldung.object.copy.exception.InvalidCopyTypeException;
import com.baeldung.object.copy.service.CloneService;
import com.baeldung.object.copy.service.DeepCloneService;
import com.baeldung.object.copy.service.DeepCopyService;
import com.baeldung.object.copy.service.ObjectCopyService;
import com.baeldung.object.copy.service.ShallowCopyService;

public class ObjectCopyServiceFactory {

	private Map<String, ObjectCopyService> copyServicesMap;

	public ObjectCopyServiceFactory(ShallowCopyService shallowCopyService, DeepCopyService deepCopyService, CloneService cloneService,
			DeepCloneService deepCloneService) {

		copyServicesMap = new TreeMap<String, ObjectCopyService>();

		copyServicesMap.put(CopyTypes.SHALLOW.getType(), shallowCopyService);
		copyServicesMap.put(CopyTypes.DEEP.getType(), deepCloneService);
		copyServicesMap.put(CopyTypes.DEEPCLONE.getType(), deepCloneService);
		copyServicesMap.put(CopyTypes.CLONE.getType(), cloneService);
	}

	public ObjectCopyService getObjectCopyService(String copyService) throws InvalidCopyTypeException {
		if (!copyServicesMap.containsKey(copyService))
			throw new InvalidCopyTypeException();
		return copyServicesMap.get(copyService);
	}
}

enum CopyTypes {
	SHALLOW {
		@Override
		public String getType() {
			return "shallow";
		}
	},
	DEEP {
		@Override
		public String getType() {
			return "deep";
		}
	},
	CLONE {
		@Override
		public String getType() {
			return "clone";
		}
	},
	DEEPCLONE {
		@Override
		public String getType() {
			return "deepclone";
		}
	};

	public abstract String getType();
}