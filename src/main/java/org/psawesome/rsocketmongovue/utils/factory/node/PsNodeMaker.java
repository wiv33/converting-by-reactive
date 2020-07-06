package org.psawesome.rsocketmongovue.utils.factory.node;

import java.util.stream.Stream;

public interface PsNodeMaker {
  <I> PsNode<I> stream(Stream<PsNode<?>> stream);
}
