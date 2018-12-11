package com.databootcamp.dataprovider.repositories;

import com.databootcamp.dataprovider.models.ModelKeyValue;
import org.junit.Test;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.util.Assert;
import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@DataMongoTest
public class KeyValueRepositoryTests {
	@Autowired
	KeyValueRepository subject;

	@Test
	public void saveTest() throws Exception {
		// first insert
		subject.save(new ModelKeyValue("test", "toto"));
		Optional<ModelKeyValue> val = subject.findById("test");
		Assert.isTrue(val.isPresent());
		Assert.isTrue(val.get().getKey().equals("test"));
		Assert.isTrue(val.get().getValue().equals("toto"));
		// update
		subject.save(new ModelKeyValue("test", "titi"));
		val = subject.findById("test");
		Assert.isTrue(val.isPresent());
		Assert.isTrue(val.get().getKey().equals("test"));
		Assert.isTrue(val.get().getValue().equals("titi"));
		// get not found key
		val = subject.findById("test1");
		Assert.isTrue(!val.isPresent());
		// delete entity
		val = subject.findById("test");
		Assert.isTrue(val.isPresent());
		subject.deleteById("test");
		val = subject.findById("test");
		Assert.isTrue(!val.isPresent());
	}
}
