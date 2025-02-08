package hash;

import java.util.*;

/**
 * The Node class represents a physical node in a consistent hashing algorithm.
 * It is responsible for managing the mapping of virtual nodes and providing methods to manipulate cache data.
 *
 * @author linyw
 */
public class Node {
    // Defines the number of virtual nodes per physical node.
    private static final int VIRTUAL_NODE_NO_PER_NODE = 1;

    // The IP address of the physical node.
    private final String ip;

    // Stores the hash values of all virtual nodes belonging to this physical node.
    private final List<Integer> virtualNodeHashes = new ArrayList<>(VIRTUAL_NODE_NO_PER_NODE);

    // Used to store cache data, with key-value pairs.
    private final Map<Object, Object> cacheMap = new HashMap<>();

    /**
     * Constructs a Node instance with a specified IP address.
     *
     * @param ip The IP address of the physical node, which cannot be null.
     */
    public Node(String ip) {
        Objects.requireNonNull(ip);
        this.ip = ip;
        initVirtualNodes();
    }

    /**
     * Initializes virtual nodes, generating and storing the hash values for all virtual nodes.
     */
    private void initVirtualNodes() {
        String virtualNodeKey;
        for (int i = 1; i <= VIRTUAL_NODE_NO_PER_NODE; i++) {
            virtualNodeKey = ip + "#" + i;
            virtualNodeHashes.add(HashUtils.hashcode(virtualNodeKey));
        }
    }

    /**
     * Adds a cache item.
     *
     * @param key The key of the cache item, cannot be null.
     * @param value The value of the cache item.
     */
    public void addCacheItem(Object key, Object value) {
        cacheMap.put(key, value);
    }

    /**
     * Retrieves a cache item based on its key.
     *
     * @param key The key of the cache item to retrieve.
     * @return The cache item, or null if not found.
     */
    public Object getCacheItem(Object key) {
        return cacheMap.get(key);
    }

    /**
     * Removes a cache item based on its key.
     *
     * @param key The key of the cache item to remove.
     */
    public void removeCacheItem(Object key) {
        cacheMap.remove(key);
    }

    /**
     * Returns the list of hash values for all virtual nodes.
     *
     * @return A list containing the hash values of all virtual nodes.
     */
    public List<Integer> getVirtualNodeHashes() {
        return virtualNodeHashes;
    }

    /**
     * Returns the IP address of the physical node.
     *
     * @return The IP address of the physical node.
     */
    public String getIp() {
        return ip;
    }

    public Map<Object, Object> getCacheMap() {
        return cacheMap;
    }

    @Override
    public String toString() {
        return "Node{" +
                "ip='" + ip + '\'' +
                '}';
    }
}
