package org.psawesome.rsocketmongovue.utils.factory.node;

public interface PsNodeMaker {
  <I> PsNode<I> stream();
}
