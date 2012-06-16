package ro.pub.master.sii.zookeeper.views;

import com.google.common.collect.ImmutableSet;
import com.yammer.dropwizard.views.View;
import ro.pub.master.sii.zookeeper.core.Node;

import java.util.Set;

public class HomeView extends View {

    private final Set<Node> nodes;

    public HomeView(Set<Node> nodes) {
        super("home.ftl");
        this.nodes = ImmutableSet.copyOf(nodes);
    }

    public Set<Node> getNodes() {
        return nodes;
    }
}
