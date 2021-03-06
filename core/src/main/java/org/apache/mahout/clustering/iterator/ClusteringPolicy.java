/**
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
package org.apache.mahout.clustering.iterator;

import java.util.List;

import org.apache.hadoop.io.Writable;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.classify.ClusterClassifier;
import org.apache.mahout.math.Vector;

/**
 * A ClusteringPolicy captures the semantics of assignment of points to clusters
 * 
 */
public interface ClusteringPolicy extends Writable {
  
  /**
   * Return a vector of weights for each of the models given those probabilities
   * 
   * @param probabilities
   *          a Vector of pdfs
   * @return a Vector of weights
   */
  Vector select(Vector probabilities);
  
  /**
   * Update the policy with the given classifier
   * 
   * @param posterior
   *          a ClusterClassifier
   */
  void update(ClusterClassifier posterior);
  
  /**
   * @param data
   *          a data Vector
   * @param models
   *          a list of Cluster models
   * @return a Vector of probabilities that the data is described by each of the
   *         models
   */
  Vector classify(Vector data, List<Cluster> models);
  
}