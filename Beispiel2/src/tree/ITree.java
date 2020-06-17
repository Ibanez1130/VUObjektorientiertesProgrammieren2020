package tree;

import rbvs.copy.IDeepCopy;
import util.searchable.ISearchableByFilter;
import tree.node.ITreeNode;

public interface ITree<TREETYPE> extends IDeepCopy, ISearchableByFilter<ITreeNode<TREETYPE>> {

	public ITreeNode<TREETYPE> getRoot();
	public void setRoot(ITreeNode<TREETYPE> root);
	public ITreeNode<TREETYPE> findNode(TREETYPE searchValue);
	public ITreeNode<TREETYPE> findNode(ITreeNode<TREETYPE> searchNode);
	public String generateConsoleView(String spacer);
	public ITree<TREETYPE> deepCopy();
}
