#pragma once

#include "CoreMinimal.h"
#include "Hex.h"
#include "Tile.h"
#include "TileDescriptor.h"
#include "GameFramework/Actor.h"
#include "HexGrid.generated.h"

UCLASS()
class TRPG_PROJET_API AHexGrid : public AActor
{
	GENERATED_BODY()

public:
	UPROPERTY(VisibleAnywhere, BlueprintReadOnly)
	FVector TopLeftBound;

protected:
	UPROPERTY(EditAnywhere, BlueprintReadWrite)
	float TileSize = 100.0f;

private:
	int LastTotalWeight = 1;

public:
	AHexGrid();

	void SetMapData(UMapData* NewMapData);
	UFUNCTION(CallInEditor, Category="Debug")
	void Refresh();
};
