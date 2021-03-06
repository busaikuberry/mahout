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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.classify.ClusterClassifier;
import org.apache.mahout.clustering.fuzzykmeans.FuzzyKMeansClusterer;
import org.apache.mahout.clustering.fuzzykmeans.SoftCluster;
import org.apache.mahout.math.Vector;

import com.google.common.collect.Lists;

/**
 * This is a probability-weighted clustering policy, suitable for fuzzy k-means
 * clustering
 * 
 */
public class FuzzyKMeansClusteringPolicy implements ClusteringPolicy {
  
  public FuzzyKMeansClusteringPolicy() {
    super();
  }
  
  private double m = 2;
  
  private double convergenceDelta = 0.05;
  
  public FuzzyKMeansClusteringPolicy(double m, double convergenceDelta) {
    this.m = m;
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see
   * org.apache.mahout.clustering.ClusteringPolicy#update(org.apache.mahout.
   * clustering.ClusterClassifier)
   */
  @Override
  public void update(ClusterClassifier posterior) {
    // nothing to do here
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see
   * org.apache.mahout.clustering.ClusteringPolicy#select(org.apache.mahout.
   * math.Vector)
   */
  @Override
  public Vector select(Vector probabilities) {
    return probabilities;
  }
  
  @Override
  public Vector classify(Vector data, List<Cluster> models) {
    Collection<SoftCluster> clusters = Lists.newArrayList();
    List<Double> distances = Lists.newArrayList();
    for (Cluster model : models) {
      SoftCluster sc = (SoftCluster) model;
      clusters.add(sc);
      distances.add(sc.getMeasure().distance(data, sc.getCenter()));
    }
    FuzzyKMeansClusterer fuzzyKMeansClusterer = new FuzzyKMeansClusterer();
    fuzzyKMeansClusterer.setM(m);
    return fuzzyKMeansClusterer.computePi(clusters, distances);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.hadoop.io.Writable#write(java.io.DataOutput)
   */
  @Override
  public void write(DataOutput out) throws IOException {
    out.writeDouble(m);
    out.writeDouble(convergenceDelta);
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see org.apache.hadoop.io.Writable#readFields(java.io.DataInput)
   */
  @Override
  public void readFields(DataInput in) throws IOException {
    this.m = in.readDouble();
    this.convergenceDelta = in.readDouble();
  }
  
}
