// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.apache.impala.catalog;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.impala.thrift.TCatalogObject;

abstract public class CatalogObjectImpl implements CatalogObject {
  // Current catalog version of this object. Initialized to
  // Catalog.INITIAL_CATALOG_VERSION.
  private AtomicLong catalogVersion_ = new AtomicLong(Catalog.INITIAL_CATALOG_VERSION);

  protected CatalogObjectImpl() {}

  @Override
  public long getCatalogVersion() { return catalogVersion_.get(); }

  @Override
  public void setCatalogVersion(long newVersion) { catalogVersion_.set(newVersion); }

  @Override
  public boolean isLoaded() { return true; }

  @Override
  public String getName() { return ""; }

  @Override
  public final String getUniqueName() {
    return Catalog.toCatalogObjectKey(toTCatalogObject());
  }

  public final TCatalogObject toTCatalogObject() {
    TCatalogObject catalogObject =
        new TCatalogObject(getCatalogObjectType(), getCatalogVersion());
    setTCatalogObject(catalogObject);
    return catalogObject;
  }

  /**
   * A template method used by {@link CatalogObjectImpl#toTCatalogObject()} for
   * populating the given catalogObject fields.
   */
  protected abstract void setTCatalogObject(TCatalogObject catalogObject);
}

