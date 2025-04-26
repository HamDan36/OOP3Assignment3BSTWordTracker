package implementations;

import java.io.Serializable;

public class BSTreeNode<E> implements Serializable
{
	private static final long serialVersionUID = 3025L;
	
	private E element;
	private BSTreeNode<E> left, right;
	
	public BSTreeNode(E e)
	{
		this.element = e;
		this.left = null;
		this.right = null;
	}
	
	public BSTreeNode(E e, BSTreeNode<E> left, BSTreeNode<E> right)
	{
		this.element = e;
		this.left = left;
		this.right = right;
	}
	
	public E getElement()
	{
		return this.element;
	}
	
	public void setElement(E e)
	{
		this.element = e;
	}
	
	public BSTreeNode<E> getLeft()
	{
		return this.left;
	}
	
	public void setLeft(BSTreeNode<E> left)
	{
		this.left = left;
	}
	
	public BSTreeNode<E> getRight()
	{
		return this.right;
	}
	
	public void setRight(BSTreeNode<E> right)
	{
		this.right = right;
	} 
	
	public boolean hasLeftChild()
	{
		return this.left != null;
	}
	
	public boolean hasRightChild()
	{
		return this.right != null;
	}
	
	public boolean isLeaf()
	{
		if (this.right == null && this.left == null)
		{
			return true;
		}
		return false;
	}
	
	public int getNumberOfNodes()
	{
		int leftCount = (left == null) ? 0 : left.getNumberOfNodes();
		int rightCount = (right == null) ? 0 : right.getNumberOfNodes();
		
		return 1 + leftCount + rightCount;
	}
	
	public int getHeight()
	{
		return getHeight(this); 
	}
	
	public int getHeight(BSTreeNode<E> node)
	{
		if (node == null) // note this returns the height at the node it is called, not height of full tree (look here for debugging)
		{
			return 0;
		}
		
		int leftHeight = getHeight(node.left);
		int rightHeight = getHeight(node.right);
		
		return 1 + Math.max(leftHeight, rightHeight);
	}
}
