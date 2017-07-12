package treecmp.metric;

import pal.tree.Tree;
import pal.misc.IdGroup;
import pal.tree.TreeUtils;
import treecmp.common.AlignInfo;
import treecmp.common.IntersectInfoMatrix;
import treecmp.common.TreeCmpException;
import treecmp.common.TreeCmpUtils;

/**
*
* @author Tomek
*/
public class MastMetricNew extends BaseMetric implements Metric {

    public MastMetricNew() {
        super();
    }

	
	@Override
	public double getDistance(Tree t1, Tree t2) {

		//run MAST for binary tree in O(nlogn) time
        if (TreeCmpUtils.isBinary(t1, true) && TreeCmpUtils.isBinary(t2, true)) {
            return getDistForBinary(t1, t2);
        }     
        //run MAST for arbitrary tree in O(n^2.5*alpha(n^2)) time
        return getDistanceForArbitrary(t1, t2);
	}

	
	private double getDistForBinary(Tree t1, Tree t2) {
        
		return 0;
	}

	/*
	 * Mast algorith for rooted binary trees based on 
	 * "Calculation, Visualization, and Manipulation of MASTs (Maximum Agreement Subtrees)"
	 * by Shiming Dong and Eileen Kraemer where the orignal algorithm form
	 * W. Goddard, E. Kubicka, G. Kubicki and F. R. McMorris. "The agreement metric for labeled
	 * binary trees", Mathematical Biosciences 123: 215-226, 1994 is described. 
	 */
	private double getDistanceForArbitrary(Tree t1, Tree t2) {
 
	    int n = t1.getExternalNodeCount();
	    if (n <= 2) {
	        return 0.0;
	    }
    
	    IdGroup id1 = TreeUtils.getLeafIdGroup(t1);
	    IntersectInfoMatrix mastIntMatrixUni = TreeCmpUtils.calcMastIntersectMatrixUni(t1, t2, id1);
	    short mast = (short) (n - mastIntMatrixUni.getSize(t1.getRoot(), t2.getRoot()));
	    
	    return mast;

	}
}
