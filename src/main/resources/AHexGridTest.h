#pragma once

#include "CoreMinimal.h"
#include "Hex.h"
#include "Tile.h"
#include "TileDescriptor.h"
#include "GameFramework/Actor.h"
#include "HexGrid.generated.h"

enum class ETileDebugType : uint8
{
    Probability,
    Weight,
};

enum ETile {
    Test,
    Test2
}

struct FTile {
    int Type = 0;
}

class AHexGrid : public AActor
{
    int Coucou;

public:
    FHex GetHex(const FVector& Location) const;
	void AddEntity(AEntity* Entity, FHex To);
	void* SetMapData(UMapData* NewMapData);
	AHexGrid();

private:
	static void SetIsmColor(UInstancedStaticMeshComponent* Ism, int Index, const FLinearColor& Color);
};
