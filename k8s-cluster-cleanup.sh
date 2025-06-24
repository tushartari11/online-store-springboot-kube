#!/opt/homebrew/bin/bash
# cleanup-cluster.sh

echo "Starting cluster cleanup..."

# Scale down all deployments
kubectl get deployments --all-namespaces -o json | jq -r '.items[] | "\(.metadata.namespace) \(.metadata.name)"' | while read namespace deployment; do
    kubectl scale deployment $deployment --replicas=0 -n $namespace
done

# Wait for pods to terminate
echo "Waiting for pods to terminate..."
sleep 30

# Delete all resources by namespace
NAMESPACES=$(kubectl get namespaces -o name | grep -v "kube-system\|kube-public\|default")
for ns in $NAMESPACES; do
    kubectl delete $ns
done

echo "Cluster cleanup completed!"