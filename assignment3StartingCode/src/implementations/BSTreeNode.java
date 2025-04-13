package implementations;

public class BSTreeNode<E>
{
	private E element;
	private BSTreeNode<E> left, right;
	
	public BSTreeNode(E element, BSTreeNode<E> left, BSTreeNode<E> right)
	{
		this.element = element;
		this.left = left;
		this.right = right;
	}
}
