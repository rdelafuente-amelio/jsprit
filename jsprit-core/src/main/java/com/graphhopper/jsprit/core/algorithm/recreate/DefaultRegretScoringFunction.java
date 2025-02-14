/*
 * Licensed to GraphHopper GmbH under one or more contributor
 * license agreements. See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 *
 * GraphHopper GmbH licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.graphhopper.jsprit.core.algorithm.recreate;

import com.graphhopper.jsprit.core.problem.job.Job;

public class DefaultRegretScoringFunction implements RegretScoringFunction {

    private final ScoringFunction scoringFunction;

    public DefaultRegretScoringFunction(ScoringFunction scoringFunction) {
        this.scoringFunction = scoringFunction;
    }

    @Override
    public double score(InsertionData best, InsertionData secondBest, Job job) {
        if (best == null) {
            throw new IllegalStateException("cannot score job " + job.getId());
        }
        double score;
        if (secondBest == null) { //either there is only one vehicle or there are more vehicles, but they cannot load unassignedJob
            //if only one vehicle, I want the job to be inserted with min iCosts
            //if there are more vehicles, I want this job to be prioritized since there are no alternatives
            score = (11 - job.getPriority()) * (Integer.MAX_VALUE - best.getInsertionCost()) + scoringFunction.score(best, job);
        } else {
            score = (11 - job.getPriority()) * (secondBest.getInsertionCost() - best.getInsertionCost()) + scoringFunction.score(best, job);
        }
        return score;
    }
}
