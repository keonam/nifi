/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.nifi.controller.queue.clustered.partition;

import org.apache.nifi.controller.repository.FlowFileRecord;

import java.util.concurrent.atomic.AtomicLong;

public class RoundRobinPartitioner implements FlowFilePartitioner {
    private final AtomicLong counter = new AtomicLong(0L);

    @Override
    public QueuePartition getPartition(final FlowFileRecord flowFile, final QueuePartition[] partitions,  final QueuePartition localPartition) {
        final long count = counter.getAndIncrement();
        final int index = (int) (count % partitions.length);
        return partitions[index];
    }

    @Override
    public boolean isRebalanceOnClusterResize() {
        return false;
    }


    @Override
    public boolean isRebalanceOnFailure() {
        return true;
    }
}
