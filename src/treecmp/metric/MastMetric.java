package treecmp.metric;

import pal.misc.IdGroup;
import pal.tree.Tree;
import pal.tree.TreeUtils;
import treecmp.common.IntersectInfoMatrix;
import treecmp.common.TreeCmpUtils;

public class MastMetric extends BaseMetric implements Metric {

	public MastMetric() {
		super();
	}

	@Override
	public double getDistance(Tree t1, Tree t2) {
	    int n = t1.getExternalNodeCount();
	    if (n <= 2) {
	        return 0.0;
	    }
	    IntersectInfoMatrix mastIntMatrix;
	    IdGroup id1 = TreeUtils.getLeafIdGroup(t1);
        if (TreeCmpUtils.isBinary(t1, true) && TreeCmpUtils.isBinary(t2, true)) {
        	/*
        	 * Mast algorithm for rooted binary trees based on 
        	 * "Calculation, Visualization, and Manipulation of MASTs (Maximum Agreement Subtrees)"
        	 * by Shiming Dong and Eileen Kraemer where the orignal algorithm form
        	 * W. Goddard, E. Kubicka, G. Kubicki and F. R. McMorris. "The agreement metric for labeled
        	 * binary trees", Mathematical Biosciences 123: 215-226, 1994 is described. 
        	 */
        	mastIntMatrix = TreeCmpUtils.calcMastIntersectMatrix(t1, t2, id1);
        }   
        else {
        	/*
        	 * Generalized Mast algorithm for rooted arbitrary trees based on 
        	 * "Calculation, Visualization, and Manipulation of MASTs (Maximum Agreement Subtrees)"
        	 * by Shiming Dong and Eileen Kraemer where the orignal algorithm form
        	 * W. Goddard, E. Kubicka, G. Kubicki and F. R. McMorris. "The agreement metric for labeled
        	 * binary trees", Mathematical Biosciences 123: 215-226, 1994 is described. 
        	 */
        	mastIntMatrix = TreeCmpUtils.calcMastIntersectMatrixUni(t1, t2, id1);
        }
        short mast = (short) (n - mastIntMatrix.getSize(t1.getRoot(), t2.getRoot()));
	    
	    return mast;
	}

}
