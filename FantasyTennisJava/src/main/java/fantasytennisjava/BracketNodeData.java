package fantasytennisjava;

class BracketNodeData {
    PlayerNodeData playerOneNodeData;
    PlayerNodeData playerTwoNodeData;

    BracketNodeData(PlayerNodeData playerOneNodeData, PlayerNodeData playerTwoNodeData) {
        this.playerOneNodeData = playerOneNodeData;
        this.playerTwoNodeData = playerTwoNodeData;
    }
}