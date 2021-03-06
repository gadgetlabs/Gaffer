/*
 * Copyright 2017 Crown Copyright
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

package uk.gov.gchq.gaffer.store.library;

import uk.gov.gchq.gaffer.commonutil.pair.Pair;
import uk.gov.gchq.gaffer.store.StoreProperties;
import uk.gov.gchq.gaffer.store.exception.OverwritingException;
import java.util.HashMap;
import java.util.Map;

public class HashMapGraphLibrary extends GraphLibrary {
    private static final Map<String, Pair<String, String>> GRAPHS = new HashMap<>();
    private static final Map<String, byte[]> SCHEMAS = new HashMap<>();
    private static final Map<String, StoreProperties> PROPERTIES = new HashMap<>();

    public HashMapGraphLibrary() {

    }

    public void initialise(final String file) {
        // Do nothing.
    }

    public static void clear() {
        GRAPHS.clear();
        SCHEMAS.clear();
        PROPERTIES.clear();
    }

    @Override
    protected void _addIds(final String graphId, final Pair<String, String> schemaAndPropsIds) throws OverwritingException {
        GRAPHS.put(graphId, schemaAndPropsIds);
    }

    @Override
    protected void _addSchema(final String schemaId, final byte[] schema) throws OverwritingException {
        SCHEMAS.put(schemaId, schema);
    }

    @Override
    protected void _addProperties(final String propertiesId, final StoreProperties properties) {
        PROPERTIES.put(propertiesId, properties);
    }

    @Override
    public Pair<String, String> getIds(final String graphId) {
        return GRAPHS.get(graphId);
    }

    @Override
    protected byte[] _getSchema(final String schemaId) {
        return SCHEMAS.get(schemaId);
    }

    @Override
    protected StoreProperties _getProperties(final String propertiesId) {
        return PROPERTIES.get(propertiesId);
    }
}
