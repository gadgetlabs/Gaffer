/*
 * Copyright 2016-2017 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.gaffer.operation.export.resultcache;

import org.junit.Test;
import uk.gov.gchq.gaffer.exception.SerialisationException;
import uk.gov.gchq.gaffer.jsonserialisation.JSONSerialiser;
import uk.gov.gchq.gaffer.operation.OperationTest;
import uk.gov.gchq.gaffer.operation.impl.export.resultcache.GetGafferResultCacheExport;

import static org.junit.Assert.assertEquals;


public class GetGafferResultCacheExportTest extends OperationTest<GetGafferResultCacheExport> {
    private static final JSONSerialiser serialiser = new JSONSerialiser();

    @Test
    public void shouldJSONSerialiseAndDeserialise() throws SerialisationException {
        // Given
        final String key = "key";
        final GetGafferResultCacheExport op = new GetGafferResultCacheExport.Builder()
                .key(key)
                .build();

        // When
        byte[] json = serialiser.serialise(op, true);
        final GetGafferResultCacheExport deserialisedOp = serialiser.deserialise(json, GetGafferResultCacheExport.class);

        // Then
        assertEquals(key, deserialisedOp.getKey());
    }

    @Test
    @Override
    public void builderShouldCreatePopulatedOperation() {
        // When
        final String key = "key";
        final GetGafferResultCacheExport op = new GetGafferResultCacheExport.Builder()
                .key(key)
                .build();

        // Then
        assertEquals(key, op.getKey());
    }

    @Override
    protected GetGafferResultCacheExport getTestObject() {
        return new GetGafferResultCacheExport();
    }
}
