#pragma once

#include "CoreMinimal.h"
#include "Hex.h"
#include "Tile.h"
#include "TileDescriptor.h"
#include "GameFramework/Actor.h"
#include "HexGrid.generated.h"

class AClass;


struct ATileDebugType: public AActor
{
	int Probability;
};

class AHexGrid : public AActor
{
    int Couou;

public:
    FHex GetHex(const FVector& Location) const;
	void AddEntity(AEntity* Entity, FHex To);
	void* SetMapData(UMapData* NewMapData);
	AHexGrid();

private:
	static void SetIsmColor(UInstancedStaticMeshComponent* Ism, int Index, const FLinearColor& Color);
};
