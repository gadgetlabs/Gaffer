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

package uk.gov.gchq.gaffer.operation.impl.output;

import com.fasterxml.jackson.core.type.TypeReference;
import uk.gov.gchq.gaffer.operation.Operation;
import uk.gov.gchq.gaffer.operation.io.InputOutput;
import uk.gov.gchq.gaffer.operation.io.MultiInput;
import uk.gov.gchq.gaffer.operation.serialisation.TypeReferenceImpl;
import java.util.Set;

/**
 * A <code>ToSet</code> operation takes in an {@link java.lang.Iterable} of items
 * and converts them to a {@link java.util.Set}, removing duplicates in the
 * process.
 *
 * @see uk.gov.gchq.gaffer.operation.impl.output.ToSet.Builder
 */
public class ToSet<T> implements
        Operation,
        InputOutput<Iterable<? extends T>, Set<? extends T>>,
        MultiInput<T> {
    private Iterable<? extends T> input;

    @Override
    public Iterable<? extends T> getInput() {
        return input;
    }

    @Override
    public void setInput(final Iterable<? extends T> input) {
        this.input = input;
    }

    @Override
    public TypeReference<Set<? extends T>> getOutputTypeReference() {
        return new TypeReferenceImpl.Set();
    }

    public static final class Builder<T>
            extends BaseBuilder<ToSet<T>, Builder<T>>
            implements InputOutput.Builder<ToSet<T>, Iterable<? extends T>, Set<? extends T>, Builder<T>>,
            MultiInput.Builder<ToSet<T>, T, Builder<T>> {
        public Builder() {
            super(new ToSet<>());
        }
    }
}
