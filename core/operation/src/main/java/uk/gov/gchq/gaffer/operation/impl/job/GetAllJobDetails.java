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

package uk.gov.gchq.gaffer.operation.impl.job;

import com.fasterxml.jackson.core.type.TypeReference;
import uk.gov.gchq.gaffer.commonutil.iterable.CloseableIterable;
import uk.gov.gchq.gaffer.jobtracker.JobDetail;
import uk.gov.gchq.gaffer.operation.Operation;
import uk.gov.gchq.gaffer.operation.io.Output;
import uk.gov.gchq.gaffer.operation.serialisation.TypeReferenceImpl;

public class GetAllJobDetails implements
        Operation,
        Output<CloseableIterable<JobDetail>> {
    @Override
    public TypeReference<CloseableIterable<JobDetail>> getOutputTypeReference() {
        return new TypeReferenceImpl.JobDetailIterable();
    }

    public static class Builder extends Operation.BaseBuilder<GetAllJobDetails, Builder>
            implements Output.Builder<GetAllJobDetails, CloseableIterable<JobDetail>, Builder> {
        public Builder() {
            super(new GetAllJobDetails());
        }
    }
}
