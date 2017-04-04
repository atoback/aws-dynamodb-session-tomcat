/*
 * Copyright 2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.amazonaws.services.dynamodb.sessionmanager;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.test.AWSTestBase;
import org.apache.catalina.Context;
import org.apache.catalina.Session;
import org.apache.catalina.startup.Tomcat;
import org.junit.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class DynamoDBSessionManagerUnitTest {

    DynamoDBSessionManager manager;

    @Before
    public void setup() {
        manager = new DynamoDBSessionManager();
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetReaperProperties() throws Exception {
        Properties properties = manager.getReaperProperties(DynamoDBSessionManager.REAPER_PROPERTIES_FILE_NAME);
        Assert.assertNotNull("Reaper properties are null", properties);

        Assert.assertFalse("Reaper properties is empty", properties.isEmpty());

        Assert.assertEquals("true", properties.getProperty(DynamoDBSessionManager.RUN_REAPER_KEY));
    }

    @Test
    public void testRunReaper() throws Exception {
        Assert.assertTrue("Reaper is not setup to run", manager.runReaper());
    }

    @Test
    public void testDoNotRunReaper() throws Exception {
        Assert.assertFalse("Reaper is setup to run", manager.runReaper("aws_dynamodb_reaper_test.properties"));
    }
}
