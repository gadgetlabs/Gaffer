/*
 * Copyright 2016 Crown Copyright
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

package uk.gov.gchq.gaffer.example.gettingstarted.analytic;

import com.google.common.collect.Lists;
import org.hamcrest.core.IsCollectionContaining;
import org.junit.Test;
import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.commonutil.iterable.CloseableIterable;
import uk.gov.gchq.gaffer.data.element.Edge;
import uk.gov.gchq.gaffer.data.element.Element;
import uk.gov.gchq.gaffer.exception.SerialisationException;
import uk.gov.gchq.gaffer.graph.Graph;
import uk.gov.gchq.gaffer.jsonserialisation.JSONSerialiser;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.operation.impl.add.AddElements;
import uk.gov.gchq.gaffer.operation.impl.get.GetElements;
import uk.gov.gchq.gaffer.user.User;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LoadAndQuery4Test {
    private static final String RESOURCE_PREFIX = "/example/gettingstarted/";
    private static final String RESOURCE_EXAMPLE_PREFIX = RESOURCE_PREFIX + "4/";
    private static final String GROUP = "data";
    private static final String COUNT = "count";
    public static final String MEAN = "mean";
    public static final String THING = "thing";

    @Test
    public void shouldReturnExpectedEdges() throws OperationException {
        // Given
        final LoadAndQuery4 query = new LoadAndQuery4();

        // When
        final CloseableIterable<? extends Element> results = query.run();

        // Then
        verifyResults(results);
    }

    @Test
    public void shouldReturnExpectedEdgesViaJson() throws OperationException, SerialisationException {
        // Given
        final User user = new User("user01");
        final JSONSerialiser serialiser = new JSONSerialiser();
        final AddElements addElements = serialiser.deserialise(StreamUtil.openStream(LoadAndQuery.class, RESOURCE_EXAMPLE_PREFIX + "json/load.json"), AddElements.class);
        final GetElements getRelatedEdges = serialiser.deserialise(StreamUtil.openStream(LoadAndQuery.class, RESOURCE_EXAMPLE_PREFIX + "json/query.json"), GetElements.class);

        // Setup graph
        final Graph graph = new Graph.Builder()
                .storeProperties(StreamUtil.openStream(LoadAndQuery.class, RESOURCE_PREFIX + "mockaccumulostore.properties"))
                .addSchemas(StreamUtil.openStreams(LoadAndQuery.class, RESOURCE_EXAMPLE_PREFIX + "schema"))
                .build();

        // When
        graph.execute(addElements, user); // Execute the add operation chain on the graph
        final CloseableIterable<? extends Element> results = graph.execute(getRelatedEdges, user); // Execute the query operation on the graph.

        // Then
        verifyResults(results);
    }

    private void verifyResults(final CloseableIterable<? extends Element> resultsItr) {
        final Edge[] expectedResults = {
                new Edge.Builder()
                        .group(GROUP)
                        .source("1")
                        .dest("2")
                        .directed(false)
                        .property(MEAN, 27.875f)
                        .property(COUNT, 8)
                        .property(THING, 223)
                        .build(),
                new Edge.Builder()
                        .group(GROUP)
                        .source("1")
                        .dest("3")
                        .directed(false)
                        .property(MEAN, 43.333332f)
                        .property(COUNT, 3)
                        .property(THING, 130)
                        .build()
        };

        final List<Element> results = Lists.newArrayList(resultsItr);
        assertEquals(expectedResults.length, results.size());
        assertThat(results, IsCollectionContaining.hasItems(expectedResults));
    }

}