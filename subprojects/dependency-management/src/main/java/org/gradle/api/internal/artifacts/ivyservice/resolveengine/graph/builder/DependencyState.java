/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.api.internal.artifacts.ivyservice.resolveengine.graph.builder;

import org.gradle.api.artifacts.ModuleIdentifier;
import org.gradle.api.artifacts.ModuleVersionSelector;
import org.gradle.api.internal.artifacts.ImmutableModuleIdentifierFactory;
import org.gradle.internal.component.model.DependencyMetadata;

class DependencyState {
    private final DependencyMetadata dependencyMetadata;
    private final ImmutableModuleIdentifierFactory moduleIdentifierFactory;

    private ModuleIdentifier moduleIdentifier;

    DependencyState(DependencyMetadata dependencyMetadata, ImmutableModuleIdentifierFactory moduleIdentifierFactory) {
        this.dependencyMetadata = dependencyMetadata;
        this.moduleIdentifierFactory = moduleIdentifierFactory;
    }

    public DependencyMetadata getDependencyMetadata() {
        return dependencyMetadata;
    }

    public ModuleIdentifier getModuleIdentifier() {
        if (moduleIdentifier == null) {
            ModuleVersionSelector requested = dependencyMetadata.getRequested();
            moduleIdentifier = moduleIdentifierFactory.module(requested.getGroup(), requested.getName());
        }
        return moduleIdentifier;
    }
}
