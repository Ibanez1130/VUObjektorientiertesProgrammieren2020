/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 03.05.2020
 */
package tree.node;

import java.util.Collection;
import rbvs.copy.IDeepCopy;

public class CopyableTreeNode<NODETYPE extends IDeepCopy> extends GenericTreeNode<NODETYPE> {

	public CopyableTreeNode(String label, NODETYPE value) {
		super(label, value);
	}

	public CopyableTreeNode<NODETYPE> deepCopy () {
		CopyableTreeNode<NODETYPE> tn = new CopyableTreeNode<NODETYPE>(this.getLabel(), this.nodeValue());
		Collection<ITreeNode<NODETYPE>> containerElements = tn.getChildren();
		for (ITreeNode<NODETYPE> c:this.getChildren()) {
			containerElements.add(c.deepCopy());
		}
		return tn;
	}
}
