/*
 * Copyright 2020 OmniFaces
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.omnifaces.persistence.test.model.enums;

import org.omnifaces.persistence.model.EnumMapping;
import org.omnifaces.persistence.model.EnumMappingTable;

@EnumMapping(fieldName = "id", enumMappingTable = @EnumMappingTable(mappingType = EnumMappingTable.MappingType.TABLE, deleteType = EnumMappingTable.DeleteAction.SOFT_DELETE))
public enum SoftDeleteOnlyIdTable {

        FIRST(1), TENTH(10), TWENTIETH(20);

        private int id;

        private SoftDeleteOnlyIdTable() {

        }

        private SoftDeleteOnlyIdTable(int id) {
                this.id = id;
        }

        public int getId() {
                return id;
        }

}
