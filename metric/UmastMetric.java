package treecmp.metric;

import pal.misc.IdGroup;
import pal.tree.Tree;
import pal.tree.TreeTool;
import pal.tree.TreeUtils;
import treecmp.common.IntersectInfoMatrix;
import treecmp.common.TreeCmpUtils;

public class UmastMetric extends BaseMetric {

	public UmastMetric() {
		super();
	}

	@Override
	public double getDistance(Tree t1, Tree t2) {
	    int n = t1.getExternalNodeCount();
	    if (n <= 2) {
	        return 0.0;
	    }
	    IntersectInfoMatrix mastIntMatrix;	    
 		Tree tree1 = TreeTool.getUnrooted(t1);
 		Tree tree2 = TreeTool.getUnrooted(t2);
 		IdGroup id1 = TreeUtils.getLeafIdGroup(tree1);
    	/*
    	 * Generalized Umast algorithm for unrooted arbitrary trees based on 
    	 * "Calculation, Visualization, and Manipulation of MASTs (Maximum Agreement Subtrees)"
    	 * by Shiming Dong and Eileen Kraemer where the orignal algorithm form
    	 * W. Goddard, E. Kubicka, G. Kubicki and F. R. McMorris. "The agreement metric for labeled
    	 * binary trees", Mathematical Biosciences 123: 215-226, 1994 is described. 
    	 */
    	mastIntMatrix = TreeCmpUtils.calcUmastIntersectMatrix(tree1, tree2, id1);

        short mast = (short) (n - mastIntMatrix.getSize(tree1.getRoot(), tree2.getRoot()));
	    
	    return mast;
	}
}
