/** This file is part of TreeCmp, a tool for comparing phylogenetic trees
 using the Matching Split distance and other metrics.
 Copyright (C) 2011,  Damian Bogdanowicz

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>. */

package treecmp.metric;
import treecmp.common.Pair;
import treecmp.common.SplitDist;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;

import pal.misc.IdGroup;
import pal.tree.*;

/**
 *
 * @author Tomek
 */
public class WRFMetric extends BaseMetric implements Metric{

    public static double getWRFDistance(Tree t1, Tree t2) {

        int n = t1.getExternalNodeCount();
        int N = t1.getInternalNodeCount();

        double dist = 0.0;
        int i;

        for (i=0;i<N;i++) {
            dist += Math.abs(t1.getExternalNode(i).getBranchLength()-t2.getExternalNode(i).getBranchLength());
        }
        IdGroup idGroup = TreeUtils.getLeafIdGroup(t1);
        ArrayList<Pair<BitSet, Double>> s_t1=SplitDist.getSplitsWithWeight(t1, idGroup);
        ArrayList<Pair<BitSet, Double>> s_t2=SplitDist.getSplitsWithWeight(t2, idGroup);
        int N1=s_t1.size();
        int N2=s_t2.size();
        int hashSetSize=(4*(N1+1))/3;

        HashSet<BitSet> s_hs=new HashSet<BitSet>(hashSetSize);

        for(i=0;i<N1;i++){
            s_hs.add(s_t1.get(i).getL());
        }

        for(i=0;i<N2;i++){
            if (s_hs.contains(s_t2.get(i).getL())){
                dist += Math.abs(s_t2.get(i).getR() - s_t1.get(i).getR());
                s_hs.remove(i);
            }
            else {
                dist += s_t2.get(i).getR();
            }
        }
        N1=s_t1.size();
        for(i=0;i<N1;i++){
            dist += s_t1.get(i).getR();
        }

        return 0.5*dist;
    }

    public double getDistance(Tree t1, Tree t2) {

        return WRFMetric.getWRFDistance(t1, t2);

    }
}
