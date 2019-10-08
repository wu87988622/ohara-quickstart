/*
 * Copyright 2019 is-land
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

/**
 * This is a simple streamApp to do the "filter" logic for specific columns {@code columnNames}.
 * We will output the row of included columns only for each data.
 */
public class FilterStreamApp extends StreamApp {

    private String key = "columnNames";

    @Override
    public StreamDefinitions config() {
        return StreamDefinitions.with(
                SettingDef.builder()
                        .key(key)
                        .displayName("columnName")
                        .documentation("filter only container these column names. Using comma separated")
                        .group("common")
                        .build());
    }

    @Override
    public void start(OStream<Row> ostream, StreamDefinitions configs) {

        ostream
                .map(
                        row -> {
                            List<String> columns = Arrays.asList(configs.string(key).split(","));
                            Cell<?>[] cells = columns.stream().map(row::cell).toArray(Cell[]::new);
                            return Row.of(cells);
                        })
                .start();
    }
}