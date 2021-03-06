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

package org.gradle.caching.local.internal;

import org.gradle.api.Action;
import org.gradle.api.UncheckedIOException;
import org.gradle.caching.BuildCacheKey;
import org.gradle.util.GFileUtils;

import java.io.File;
import java.io.IOException;

public class DefaultBuildCacheTempFileStore implements BuildCacheTempFileStore {

    private final File dir;
    private final String partialFileSuffix;

    public DefaultBuildCacheTempFileStore(File dir, String partialFileSuffix) {
        this.dir = dir;
        this.partialFileSuffix = partialFileSuffix;
        GFileUtils.mkdirs(this.dir);
    }

    @Override
    public void allocateTempFile(BuildCacheKey key, Action<? super File> action) {
        String hashCode = key.getHashCode();
        final File tempFile;
        try {
            tempFile = File.createTempFile(hashCode, partialFileSuffix, dir);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }

        try {
            action.execute(tempFile);
        } finally {
            if (tempFile.exists()) {
                GFileUtils.deleteQuietly(tempFile);
            }
        }
    }

}
