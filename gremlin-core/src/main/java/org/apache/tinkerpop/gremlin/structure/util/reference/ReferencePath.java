/*
 *
 *  * Licensed to the Apache Software Foundation (ASF) under one
 *  * or more contributor license agreements.  See the NOTICE file
 *  * distributed with this work for additional information
 *  * regarding copyright ownership.  The ASF licenses this file
 *  * to you under the Apache License, Version 2.0 (the
 *  * "License"); you may not use this file except in compliance
 *  * with the License.  You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing,
 *  * software distributed under the License is distributed on an
 *  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  * KIND, either express or implied.  See the License for the
 *  * specific language governing permissions and limitations
 *  * under the License.
 *
 */

package org.apache.tinkerpop.gremlin.structure.util.reference;

import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.step.util.MutablePath;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.util.Attachable;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class ReferencePath extends MutablePath implements Attachable<Path> {

    private ReferencePath() {

    }

    public Path getBase() {
        return this;
    }

    protected ReferencePath(final Path path) {
        path.forEach((object, labels) -> {
            if (object instanceof ReferenceElement || object instanceof ReferenceProperty || object instanceof ReferencePath) {
                this.objects.add(object);
                this.labels.add(labels);
            } else if (object instanceof Element) {
                this.objects.add(ReferenceFactory.detach((Element) object));
                this.labels.add(labels);
            } else if (object instanceof Property) {
                this.objects.add(ReferenceFactory.detach((Property) object));
                this.labels.add(labels);
            } else if (object instanceof Path) {
                this.objects.add(ReferenceFactory.detach((Path) object));
                this.labels.add(labels);
            } else {
                this.objects.add(object);
                this.labels.add(labels);
            }
        });
    }

    @Override
    public Path attach(final Graph hostGraph, final Method method) {
        final Path path = MutablePath.make();  // TODO: Use ImmutablePath?
        this.forEach((object, labels) -> path.extend(object instanceof Attachable ? ((Attachable) object).attach(hostGraph, method) : object, labels.toArray(new String[labels.size()])));
        return path;
    }

    @Override
    public Path attach(final Vertex hostVertex, final Method method) {
        final Path path = MutablePath.make();  // TODO: Use ImmutablePath?
        this.forEach((object, labels) -> path.extend(object instanceof Attachable ? ((Attachable) object).attach(hostVertex, method) : object, labels.toArray(new String[labels.size()])));
        return path;
    }
}