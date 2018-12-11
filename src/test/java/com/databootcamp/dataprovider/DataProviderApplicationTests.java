package com.databootcamp.dataprovider;

import com.databootcamp.dataprovider.models.ModelKeyValue;
import org.junit.Test;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@RunWith(SpringRunner.class)
@DataMongoTest
public class DataProviderApplicationTests {
    @Test
    public void contextLoads() {
    }
}
