/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package treecmp.common;

import java.io.PrintWriter;
import pal.io.FormattedOutput;
import pal.io.OutputTarget;
import pal.tree.Node;
import pal.tree.NodeUtils;
import pal.tree.Tree;

/**
 *
 * @author Damian
 */
public class NodeUtilsExt extends NodeUtils {

    public static int printNH(PrintWriter out, Node node,
            boolean printLengths, boolean printInternalLabels, int column, int fractionDigits) {

        if (!node.isLeaf()) {
            out.print("(");
            column++;

            for (int i = 0; i < node.getChildCount(); i++) {
                if (i != 0) {
                    out.print(",");
                    column++;
                }

                column = printNH(out, node.getChild(i), printLengths, printInternalLabels, column, fractionDigits);
            }

            out.print(")");
            column++;
        }

        if (!node.isRoot()) {
            if (node.isLeaf() || printInternalLabels) {

                String id = node.getIdentifier().toString();
                out.print(id);
                column += id.length();
            }

            if (printLengths) {
                out.print(":");
                column++;

                column += FormattedOutput.getInstance().displayDecimal(out, node.getBranchLength(), fractionDigits);
            }
        }
        return column;
    }


    public static String treeToSimpleString(Tree tree, boolean printLengths) {
        return treeToSimpleString(tree.getRoot(), printLengths);
    }

    public static String treeToSimpleString(Node node, boolean printLengths) {
        OutputTarget out = OutputTarget.openString();
        NodeUtilsExt.printNH(out, node, printLengths, false, 0, 6);
        String treeNewick = out.getString();
        out.close();
        return treeNewick;
    }
}
