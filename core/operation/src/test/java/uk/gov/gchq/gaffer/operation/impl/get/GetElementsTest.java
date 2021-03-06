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

package uk.gov.gchq.gaffer.operation.impl.get;

import org.junit.Test;
import uk.gov.gchq.gaffer.data.element.id.DirectedType;
import uk.gov.gchq.gaffer.data.element.id.ElementId;
import uk.gov.gchq.gaffer.data.elementdefinition.view.View;
import uk.gov.gchq.gaffer.exception.SerialisationException;
import uk.gov.gchq.gaffer.jsonserialisation.JSONSerialiser;
import uk.gov.gchq.gaffer.operation.Operation;
import uk.gov.gchq.gaffer.operation.OperationTest;
import uk.gov.gchq.gaffer.operation.SeedMatching.SeedMatchingType;
import uk.gov.gchq.gaffer.operation.data.EdgeSeed;
import uk.gov.gchq.gaffer.operation.data.ElementSeed;
import uk.gov.gchq.gaffer.operation.data.EntitySeed;
import uk.gov.gchq.gaffer.operation.graph.SeededGraphFilters;
import java.util.Iterator;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class GetElementsTest extends OperationTest {
    private static final JSONSerialiser serialiser = new JSONSerialiser();

    @Override
    public Class<? extends Operation> getOperationClass() {
        return GetElements.class;
    }

    @Test
    public void shouldSetSeedMatchingTypeToEquals() {
        // Given
        final ElementId elementId1 = new EntitySeed("identifier");

        // When
        final GetElements op = new GetElements.Builder()
                .input(elementId1)
                .seedMatching(SeedMatchingType.EQUAL)
                .build();

        // Then
        assertEquals(SeedMatchingType.EQUAL, op.getSeedMatching());
    }

    private void shouldSerialiseAndDeserialiseOperationWithElementIds() throws SerialisationException {
        // Given
        final ElementSeed elementSeed1 = new EntitySeed("identifier");
        final ElementSeed elementSeed2 = new EdgeSeed("source2", "destination2", true);
        final GetElements op = new GetElements.Builder()
                .input(elementSeed1, elementSeed2)
                .build();

        // When
        byte[] json = serialiser.serialise(op, true);
        final GetElements deserialisedOp = serialiser.deserialise(json, GetElements.class);

        // Then
        final Iterator itr = deserialisedOp.getInput().iterator();
        assertEquals(elementSeed1, itr.next());
        assertEquals(elementSeed2, itr.next());
        assertFalse(itr.hasNext());
    }

    private void builderShouldCreatePopulatedOperationAll() {
        final GetElements op = new GetElements.Builder()
                .input(new EntitySeed("A"))
                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.EITHER)
                .view(new View.Builder()
                        .edge("testEdgeGroup")
                        .build())
                .build();

        assertEquals(SeededGraphFilters.IncludeIncomingOutgoingType.EITHER,
                op.getIncludeIncomingOutGoing());
        assertNotNull(op.getView());
    }

    @Test
    public void shouldSetSeedMatchingTypeToRelated() {
        final ElementId elementId1 = new EntitySeed("identifier");
        final ElementId elementId2 = new EdgeSeed("source2", "destination2", true);

        // When
        final GetElements op = new GetElements.Builder()
                .input(elementId1, elementId2)
                .seedMatching(SeedMatchingType.RELATED)
                .build();

        // Then
        assertEquals(SeedMatchingType.RELATED, op.getSeedMatching());
    }

    private void builderShouldCreatePopulatedOperationIncoming() {
        ElementSeed seed = new EntitySeed("A");
        GetElements op = new GetElements.Builder()
                .input(seed)
                .inOutType(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING)
                .view(new View.Builder()
                        .edge("testEdgeGroup")
                        .build())
                .build();
        assertEquals(SeededGraphFilters.IncludeIncomingOutgoingType.INCOMING,
                op.getIncludeIncomingOutGoing());
        assertNotNull(op.getView());
        assertEquals(seed, op.getInput().iterator().next());
    }

    @Test
    public void shouldSetDirectedTypeToBoth() {
        // When
        final GetElements op = new GetElements.Builder()
                .directedType(DirectedType.EITHER)
                .build();

        // Then
        assertEquals(DirectedType.EITHER, op.getDirectedType());
    }

    @Test
    public void shouldSetOptionToValue() {
        // When
        final GetElements op = new GetElements.Builder()
                .option("key", "value")
                .build();

        // Then
        assertThat(op.getOptions(), is(notNullValue()));
        assertThat(op.getOptions().get("key"), is("value"));
    }

    @Test
    @Override
    public void shouldSerialiseAndDeserialiseOperation() throws SerialisationException {
        shouldSerialiseAndDeserialiseOperationWithElementIds();
    }

    @Test
    @Override
    public void builderShouldCreatePopulatedOperation() {
        builderShouldCreatePopulatedOperationAll();
        builderShouldCreatePopulatedOperationIncoming();
    }
}
